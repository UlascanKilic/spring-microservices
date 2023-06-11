package ulascan.userservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
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
import ulascan.userservice.exception.ConflictException;
import ulascan.userservice.exception.Error;
import ulascan.userservice.kafka.SendMailEvent;
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

    private final KafkaTemplate<String, SendMailEvent> kafkaTemplate;

    /**
     * Registers a new user with the provided information.
     * Generates a random verification code and saves it to the user entity.
     * Performs user validation and saves the user to the repository.
     * Generates an access token and a refresh token for the registered user.
     * @param request RegisterRequestDTO object containing the user registration information.
     * @return AuthenticationResponseDTO object containing the access token and refresh token.
     * @throws BadRequestException if the email is already in use.
     */
    @Transactional
    public void register(RegisterRequestDTO request) {
        Map<String, Object> roleClaims = new HashMap<>();

        String randomCode = RandomString.make(64);

        if(repository.findByEmail(request.getEmail()) != null)
            throw new ConflictException(Error.EMAIL_IS_IN_USE.getErrorCode(), Error.EMAIL_IS_IN_USE.getErrorMessage());

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .verificationCode(randomCode)
                .resetPasswordCode("")
                .activated(true) //TODO DEGİS
                .isPrivileged(false)
                .build();

        //TODO for the test purposes
        if(user.getFirstName().contains("server"))
            user.setRole(Role.SERVER);

        kafkaTemplate.send("notificationTopic", SendMailEvent.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .verificationCode(user.getVerificationCode())
                        .build());

        //JWT
        //List<String> roleStrings = jwtService.convertAuthoritiesToStringList((List<SimpleGrantedAuthority>) user.getAuthorities());
        String roleString = String.valueOf(user.getRole());
        roleClaims.put("role", roleString);
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(roleClaims, user);
        var refreshToken = jwtService.generateRefreshToken(roleClaims,user);
        //saveUserToken(savedUser, jwtToken);
        /*return  AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();*/
    }

    /**
     * Authenticates a user with the provided credentials.
     * Performs user authentication using the Spring Security authentication manager.
     * Generates an access token and a refresh token for the authenticated user.
     * @param request AuthenticationRequestDTO object containing the user's email and password.
     * @return AuthenticationResponseDTO object containing the access token, refresh token, and user information.
     * @throws BadRequestException if the user doesn't exist or the password is incorrect.
     */
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
        //TODO: Uncomment the line below
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

    /**
     * Verifies the user's account using the provided verification code.
     * Checks if the verification code matches the stored code and updates the user's activation status.
     * @param code The verification code to validate.
     * @return true if the verification is successful, false otherwise.
     * @throws BadRequestException if the verification code is invalid.
     */
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
