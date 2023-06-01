package ulascan.userservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ulascan.userservice.dto.AuthenticationRequestDTO;
import ulascan.userservice.dto.AuthenticationResponseDTO;
import ulascan.userservice.dto.RegisterRequestDTO;
import ulascan.userservice.entity.*;
import ulascan.userservice.exception.BadRequestException;
import ulascan.userservice.exception.Error;
import ulascan.userservice.repository.TokenRepository;
import ulascan.userservice.repository.UserRepository;
import ulascan.userservice.utils.RandomString;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        Map<String, Object> roleClaims = new HashMap<>();

        String randomCode = RandomString.make(64);

        if(repository.findByEmail(request.getEmail()) != null)
            throw new BadRequestException(Error.EMAIL_IS_IN_USE.getErrorCode(), Error.EMAIL_IS_IN_USE.getErrorMessage());

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .verificationCode(randomCode)
                .resetPasswordCode("")
                .activated(false)
                .isPrivileged(false)
                .build();


        //TODO send mail mailService.sendVerificationEmail(userEntity, "http://193.140.7.178:7769");

        //JWT
        //List<String> roleStrings = jwtService.convertAuthoritiesToStringList((List<SimpleGrantedAuthority>) user.getAuthorities());
        String roleString = String.valueOf(user.getRole());
        roleClaims.put("role", roleString);
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(roleClaims, user);
        var refreshToken = jwtService.generateRefreshToken(roleClaims,user);
        //saveUserToken(savedUser, jwtToken);
        return  AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Map<String, Object> roleClaims = new HashMap<>();
        User user = repository.findByEmail(request.getEmail());

        if(user == null) throw new BadRequestException(Error.USER_DOESNT_EXIST.getErrorCode(), Error.USER_DOESNT_EXIST.getErrorMessage());

        //filter chain
        //TODO UNDO COMMENT LINE
        //if(!user.isActivated()) throw new BadRequestException(Error.INACTIVE_USER.getErrorCode(), Error.INACTIVE_USER.getErrorMessage());

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new BadRequestException(Error.WRONG_PASSWORD.getErrorCode(), Error.WRONG_PASSWORD.getErrorMessage());

        //JWT
        //List<String> roleStrings = jwtService.convertAuthoritiesToStringList((List<SimpleGrantedAuthority>) user.getAuthorities());
        String roleString = String.valueOf(user.getRole());
        roleClaims.put("role", roleString);
        var jwtToken = jwtService.generateToken(roleClaims, user);
        var refreshToken = jwtService.generateRefreshToken(roleClaims,user);
        //revokeAllUserTokens(user);
        //saveUserToken(user, jwtToken);

         return AuthenticationResponseDTO.builder()
                 .accessToken(jwtToken)
                 .refreshToken(refreshToken)
                 .firstName(user.getFirstName())
                 .lastName(user.getLastName())
                 .role(user.getRole().name())
                 .build();

    }

    /*private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }*/


    /*private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }*/

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail);

            if(user == null) throw new BadRequestException(Error.USER_DOESNT_EXIST.getErrorCode(), Error.USER_DOESNT_EXIST.getErrorMessage());

            if (jwtService.isTokenValid(refreshToken, user)) {
                Map<String, Object> roleClaims = new HashMap<>();
                List<String> roleStrings = jwtService.convertAuthoritiesToStringList((List<SimpleGrantedAuthority>) user.getAuthorities());
                roleClaims.put("role", roleStrings);
                var accessToken = jwtService.generateToken(user);
                //revokeAllUserTokens(user);
                //saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponseDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Transactional
    public boolean verify(String code) {
        User user = repository.findByVerificationCode(code);

        if(user == null) throw new BadRequestException(Error.VERIFICATION_DOESNT_EXIST.getErrorCode(), Error.VERIFICATION_DOESNT_EXIST.getErrorMessage());

        if(!user.isActivated() && user.getVerificationCode() != null && user.getVerificationCode().equals(code))
        {
            user.setVerificationCode(null);
            user.setActivated(true);
            repository.save(user);

            return true;
        }
        return false;
    }
}
