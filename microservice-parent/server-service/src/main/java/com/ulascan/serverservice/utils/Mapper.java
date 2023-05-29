package com.ulascan.serverservice.utils;

import com.ulascan.serverservice.dto.ServerCountDTO;
import com.ulascan.serverservice.dto.ServerDTO;
import com.ulascan.serverservice.dto.StartSceneDTO;
import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.entity.Server;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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

    public Scene dtoToEntity(StartSceneDTO startSceneDTO, Scene scene)
    {
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.modelMapper.map(startSceneDTO, scene);
        scene.setActive(false);
        scene.setServer(null);
        scene.setPrivateScene(scene.getScenePassword().isEmpty());
        return scene;

    }

    public Server dtoToEntity(ServerDTO serverDTO, Server server) {
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        this.modelMapper.map(serverDTO, server);

        server.setPort(serverDTO.getServerName().substring(serverDTO.getServerName().length() - 4));

        //server.setFull(serverDTO.getUserCount() >= server.getScene().getMaxUserCapacity());
        return server;
    }

    public Server dtoToEntity(ServerCountDTO serverCountDTO, Server server) {
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);

        this.modelMapper.map(serverCountDTO, server);

       // server.setFull(serverCountDTO.getUserCount() >= server.getScene().getMaxUserCapacity());
        return server;
    }

    public ServerDTO entityToDTO(Server server) {
        return modelMapper.map(server, ServerDTO.class);
    }
}
