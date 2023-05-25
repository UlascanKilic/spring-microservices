package ulascan.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ulascan.userservice.dto.AuthenticationRequestDTO;
import ulascan.userservice.dto.AuthenticationResponseDTO;
import ulascan.userservice.dto.RegisterRequestDTO;
import ulascan.userservice.service.AuthenticationService;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequestDTO request
    ) {
        return service.register(request);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequestDTO request
    ) {
        return service.authenticate(request);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
}
