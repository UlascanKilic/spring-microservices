package ulascan.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ulascan.userservice.dto.EmailDTO;
import ulascan.userservice.dto.ResetPasswordDTO;
import ulascan.userservice.dto.UserDTO;
import ulascan.userservice.entity.User;
import ulascan.userservice.exception.BadRequestException;
import ulascan.userservice.exception.Error;
import ulascan.userservice.repository.UserRepository;
import ulascan.userservice.utils.Mapper;
import ulascan.userservice.utils.RandomString;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Mapper mapper;

    @Transactional
    public void forgotPassword(EmailDTO email) {
        User user = userByEmail(email.getEmail());

        String randomCode = RandomString.make(6);

        user.setResetPasswordCode(randomCode);
        userRepository.save(user);

        //TODO SEND MAIL

    }

    @Transactional
    public UserDTO resetPassword(ResetPasswordDTO dto){

        User user = userByEmail(dto.getEmail());

        if(user.getResetPasswordCode() == null)
            throw new BadRequestException(Error.RESET_PASSWORD_CODE_NOT_FOUND.getErrorCode(), Error.RESET_PASSWORD_CODE_NOT_FOUND.getErrorMessage());

        if(!user.getResetPasswordCode().equals(dto.getPasswordCode()))
            throw new BadRequestException(Error.RESET_PASSWORD_CODE_DOESNT_MATCH.getErrorCode(), Error.RESET_PASSWORD_CODE_DOESNT_MATCH.getErrorMessage());

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setResetPasswordCode(null);

        userRepository.save(user);

        return mapper.entityToDTO(user);

    }

    public User userByEmail(String email){
        User user = userRepository.findByEmail(email);

        if(user == null)  throw new BadRequestException(Error.USER_DOESNT_EXIST.getErrorCode(), Error.USER_DOESNT_EXIST.getErrorMessage());

        return user;
    }

}
