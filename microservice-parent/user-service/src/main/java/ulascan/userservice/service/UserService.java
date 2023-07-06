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

    /**
     * Retrieves all users.
     * @return List of UserDTO objects representing all users.
     */
    public List<UserDTO> getAllUsers(){
        //get all users
        //convert into userDTO
        List<User> users = userRepository.findAll();
        return mapper.mapList(users, UserDTO.class);
    }

    /**
     * Retrieves a user by ID.
     * @param userId The ID of the user to retrieve.
     * @return UserDTO object representing the retrieved user.
     * @throws BadRequestException if the user doesn't exist.
     */
    public UserDTO getUserById(Long userId){
        //Find user by id
        //convert into userDTO
        User user = userByPublicId(userId);
        return mapper.entityToDTO(user);
    }
    public UserDTO getUserByPublicId(Long publicId){
        //Find user by id
        //convert into userDTO
        User user = userByPublicId(publicId);
        return mapper.entityToDTO(user);
    }

    /**
     * Updates a user by ID.
     * @param userId The ID of the user to update.
     * @param userDTO UserDTO object containing updated user information.
     */
    public void updateUserById(Long userId, UserDTO userDTO)
    {
        User user = userByPublicId(userId);
        user = mapper.dtoToEntity(userDTO,user);
        userRepository.save(user);
    }

    /**
     * Deletes a user by ID.
     * @param userId The ID of the user to delete.
     */
    public void deleteUserById(Long userId)
    {
        User user = userByPublicId(userId);
        userRepository.delete(user);

    }

    /**
     * Retrieves a user by Public ID.
     * @param publicId The Public ID of the user to retrieve.
     * @return User object representing the retrieved user.
     * @throws BadRequestException if the user doesn't exist.
     */
    public User userByPublicId(Long publicId){
        if(userRepository.findByPublicId(publicId).isEmpty())
            throw new BadRequestException(Error.USER_DOESNT_EXIST.getErrorCode(), Error.USER_DOESNT_EXIST.getErrorMessage());

        return userRepository.findByPublicId(publicId).get();
    }
}
