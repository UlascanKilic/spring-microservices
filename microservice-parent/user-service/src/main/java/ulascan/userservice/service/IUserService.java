package ulascan.userservice.service;

import ulascan.userservice.dto.UserDTO;
import ulascan.userservice.entity.User;

import java.util.List;

public interface IUserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Integer userId);
    void updateUserById(Integer userId, UserDTO userDTO);
    void deleteUserById(Integer userId);
    User userById(Integer userId);
}
