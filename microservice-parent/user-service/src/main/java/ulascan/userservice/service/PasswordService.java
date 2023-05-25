package ulascan.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ulascan.userservice.dto.EmailDTO;
import ulascan.userservice.dto.ResetPasswordDTO;
import ulascan.userservice.entity.User;
import ulascan.userservice.repository.UserRepository;
import ulascan.userservice.utils.Mapper;
import ulascan.userservice.utils.RandomString;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void forgotPassword(EmailDTO email) {
        User user = userByEmail(email.getEmail());

        String randomCode = RandomString.make(6);

        user.setResetPasswordCode(randomCode);
        userRepository.save(user);

        //TODO SEND MAIL

    }

    public ResponseEntity<?> resetPassword(ResetPasswordDTO dto){
        User user = userByEmail(dto.getEmail());

        if(user.getResetPasswordCode() == null) return new ResponseEntity<String>("User's Password Code is Null!", HttpStatus.FORBIDDEN);
        if(!user.getResetPasswordCode().equals(dto.getPasswordCode())) return new ResponseEntity<String>("Wrong Code!", HttpStatus.UNAUTHORIZED);

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setResetPasswordCode(null);

        userRepository.save(user);

        return ResponseEntity.ok().build();

    }

    public User userByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
