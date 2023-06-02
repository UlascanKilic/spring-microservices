package ulascan.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ulascan.userservice.dto.EmailDTO;
import ulascan.userservice.dto.ResetPasswordDTO;
import ulascan.userservice.dto.UserDTO;
import ulascan.userservice.service.PasswordService;

@RestController
@RequestMapping("/api/user/password")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @PostMapping("/forgot")
    @ResponseStatus(HttpStatus.OK)
    public void forgotPassword(@RequestBody EmailDTO email)
    {
        passwordService.forgotPassword(email);
    }

    @PutMapping("/reset")
    public ResponseEntity<UserDTO> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO){
        return ResponseEntity.ok(passwordService.resetPassword(resetPasswordDTO));
    }
}
