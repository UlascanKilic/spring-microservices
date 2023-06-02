package com.ulascan.serverservice.utils;

import com.ulascan.serverservice.dto.ServerCountDTO;
import com.ulascan.serverservice.dto.ServerRequestDTO;
import com.ulascan.serverservice.dto.SceneRequestDTO;
import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.entity.Server;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class Mapper {
    @Autowired
    private ModelMapper modelMapper;

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    /*<SceneResponseDTO, Scene> sceneMapping = new PropertyMap<SceneResponseDTO, Scene>() {
        protected void configure() {
            map().getServer().setUserCount(source.getUserCount());
            map().getServer().setMaxUserCapacity(source.getMaxUserCapacity());
        }
    };*/

    public Scene dtoToEntity(SceneRequestDTO sceneRequestDTO, Scene scene)
    {
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.modelMapper.map(sceneRequestDTO, scene);
        scene.setActive(false);
        scene.setServer(null);
        scene.setPrivateScene(sceneRequestDTO.isPrivateScene());
        if(scene.isPrivateScene()){
            String password = RandomString.make(5);
            scene.setScenePassword(password);
        }
        else{
            scene.setScenePassword("");
        }
        return scene;

    }

    public Server dtoToEntity(ServerRequestDTO serverRequestDTO, Server server) {
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        this.modelMapper.map(serverRequestDTO, server);

        server.setPort(serverRequestDTO.getServerName().substring(serverRequestDTO.getServerName().length() - 4));

        //server.setFull(serverDTO.getUserCount() >= server.getScene().getMaxUserCapacity());
        return server;
    }

    public Server dtoToEntity(ServerCountDTO serverCountDTO, Server server) {
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);

        this.modelMapper.map(serverCountDTO, server);

       // server.setFull(serverCountDTO.getUserCount() >= server.getScene().getMaxUserCapacity());
        return server;
    }

    public ServerRequestDTO entityToDTO(Server server) {
        return modelMapper.map(server, ServerRequestDTO.class);
    }
}
