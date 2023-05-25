package ulascan.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ulascan.userservice.dto.UserDTO;
import ulascan.userservice.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers(){
        //get all users
        //convert into userDTO
        return null;
    }

    public UserDTO getUserById(Integer userId){
        //Find user by id
        //convert into userDTO
        return null;
    }

    public void updateUserById(Integer userId, UserDTO userDTO)
    {
        //find user by id
        //convert user into dto that given in params
        //save as entity
    }

    public void deleteUserById(Integer userId)
    {
        //find user by id
        //if user is not null
        //delete

    }
}
