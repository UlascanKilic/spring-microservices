package ulascan.userservice.utils;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ulascan.userservice.dto.UserDTO;
import ulascan.userservice.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class Mapper {

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO entityToDTO(User entity){
        return modelMapper.map(entity, UserDTO.class);
    }

    public User dtoToEntity(UserDTO dto, User user){
        /*TypeMap<UserDTO, User> propertyMapper = this.modelMapper.createTypeMap(UserDTO.class, User.class);
        propertyMapper.addMappings(
                mapper -> mapper.map(UserDTO::getEmail, User::setEmail)
        );

        this.modelMapper.getConfiguration().setSkipNullEnabled(true);*/

        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
        this.modelMapper.map(dto, user);
       /* user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setAvatarName(dto.getAvatarName());
        user.setPrivileged(dto.isPrivileged());*/
        return user;
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}
