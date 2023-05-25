package ulascan.userservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ulascan.userservice.dto.AuthenticationRequestDTO;
import ulascan.userservice.dto.AuthenticationResponseDTO;
import ulascan.userservice.dto.RegisterRequestDTO;
import ulascan.userservice.entity.Role;
import ulascan.userservice.entity.Token;
import ulascan.userservice.entity.TokenType;
import ulascan.userservice.entity.User;
import ulascan.userservice.repository.TokenRepository;
import ulascan.userservice.repository.UserRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        Map<String, Object> roleClaims = new HashMap<>();
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        //List<String> roleStrings = jwtService.convertAuthoritiesToStringList((List<SimpleGrantedAuthority>) user.getAuthorities());
        String roleString = String.valueOf(user.getRole());
        roleClaims.put("role", roleString);
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(roleClaims, user);
        var refreshToken = jwtService.generateRefreshToken(roleClaims,user);
        //saveUserToken(savedUser, jwtToken);
        return AuthenticationResponseDTO.builder()
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
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
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
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

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
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
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
}
