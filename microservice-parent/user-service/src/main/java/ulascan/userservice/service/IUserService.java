package ulascan.userservice.service;

import ulascan.userservice.dto.UserDTO;
import ulascan.userservice.entity.User;

import java.util.List;

public interface IUserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long userId);
    void updateUserById(Long userId, UserDTO userDTO);
    void deleteUserById(Long userId);
    User userByPublicId(Long userId);
}
