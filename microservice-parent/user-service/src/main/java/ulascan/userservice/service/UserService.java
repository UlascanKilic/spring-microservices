package ulascan.userservice.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ulascan.userservice.dto.UserDTO;
import ulascan.userservice.entity.User;
import ulascan.userservice.exception.BadRequestException;
import ulascan.userservice.exception.Error;
import ulascan.userservice.repository.UserRepository;
import ulascan.userservice.utils.Mapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService{

    //TODO server userları icin logic??
    private final UserRepository userRepository;

    private final Mapper mapper;

    public List<UserDTO> getAllUsers(){
        //get all users
        //convert into userDTO
        List<User> users = userRepository.findAll();
        return mapper.mapList(users, UserDTO.class);
    }

    public UserDTO getUserById(Integer userId){
        //Find user by id
        //convert into userDTO
        User user = userById(userId);
        return mapper.entityToDTO(user);
    }

    public void updateUserById(Integer userId, UserDTO userDTO)
    {
        User user = userById(userId);
        user = mapper.dtoToEntity(userDTO,user);
        userRepository.save(user);
    }

    public void deleteUserById(Integer userId)
    {
        User user = userById(userId);
        userRepository.delete(user);

    }

    public User userById(Integer userId){
        if(userRepository.findById(userId).isEmpty())
            throw new BadRequestException(Error.USER_DOESNT_EXIST.getErrorCode(), Error.USER_DOESNT_EXIST.getErrorMessage());

        return userRepository.findById(userId).get();
    }
}
