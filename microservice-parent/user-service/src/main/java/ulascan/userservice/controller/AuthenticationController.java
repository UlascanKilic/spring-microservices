package ulascan.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulascan.userservice.dto.AuthenticationRequestDTO;
import ulascan.userservice.dto.AuthenticationResponseDTO;
import ulascan.userservice.dto.RegisterRequestDTO;
import ulascan.userservice.service.AuthenticationService;

import java.io.IOException;
import java.net.URI;

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

    @GetMapping("/verify")
    public ResponseEntity<Void> verify(@Param("code") String code){
        if(service.verify(code))
        {
            //TO DO:
            //verify fail olursa sitede fail olan bir sayfaya yönlendirilecek
            //verify fail olmazsa sitede başarılı! olan bir sayfaya yönlendirilecek
        }
        else {
            //return "verify_fail";
        }
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("https://ytustarverse.com/")).build();
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
}
