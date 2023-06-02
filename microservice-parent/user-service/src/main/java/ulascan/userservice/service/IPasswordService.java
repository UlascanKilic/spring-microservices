package ulascan.userservice.service;

import ulascan.userservice.dto.EmailDTO;
import ulascan.userservice.dto.ResetPasswordDTO;
import ulascan.userservice.dto.UserDTO;
import ulascan.userservice.entity.User;

public interface IPasswordService {
    void forgotPassword(EmailDTO email);
    UserDTO resetPassword(ResetPasswordDTO dto);
    User userByEmail(String email);
}
