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
public class PasswordService implements IPasswordService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Mapper mapper;

    /**
     * Initiates the password reset process for a user.
     * Generates a random code, associates it with the user, and saves it to the database.
     * @param email EmailDTO object containing the user's email address.
     */
    @Transactional
    public void forgotPassword(EmailDTO email) {
        User user = userByEmail(email.getEmail());

        String randomCode = RandomString.make(6);

        user.setResetPasswordCode(randomCode);
        userRepository.save(user);

        //TODO SEND MAIL

    }

    /**
     * Resets the password for a user.
     * Validates the provided password code against the user's stored code.
     * If the codes match, updates the user's password and clears the reset code.
     * @param dto ResetPasswordDTO object containing the user's email, password code, and new password.
     * @return UserDTO object representing the user with the updated password.
     * @throws BadRequestException if the password code is not found or doesn't match.
     */
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

    /**
     * Retrieves a user by email.
     * @param email The email address of the user to retrieve.
     * @return User object representing the retrieved user.
     * @throws BadRequestException if the user doesn't exist.
     */
    public User userByEmail(String email){
        User user = userRepository.findByEmail(email);

        if(user == null)  throw new BadRequestException(Error.USER_DOESNT_EXIST.getErrorCode(), Error.USER_DOESNT_EXIST.getErrorMessage());

        return user;
    }

}
