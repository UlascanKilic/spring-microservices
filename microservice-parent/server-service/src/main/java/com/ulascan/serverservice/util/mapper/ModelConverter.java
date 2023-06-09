package com.ulascan.serverservice.util.mapper;

import com.ulascan.serverservice.dto.scene.event.EventRequestDTO;
import com.ulascan.serverservice.dto.scene.event.EventResponseDTO;
import com.ulascan.serverservice.dto.scene.session.SessionRequestDTO;
import com.ulascan.serverservice.dto.scene.session.SessionResponseDTO;
import com.ulascan.serverservice.dto.server.ServerCountDTO;
import com.ulascan.serverservice.dto.server.ServerRequestDTO;
import com.ulascan.serverservice.dto.scene.SceneRequestDTO;
import com.ulascan.serverservice.entity.EventEntity;
import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.entity.Server;
import com.ulascan.serverservice.entity.SessionEntity;
import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.util.RandomString;
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
public class ModelConverter implements IModelConverter {
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

   /* public Scene dtoToEntity(SceneRequestDTO sceneRequestDTO, Scene scene)
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

    }*/

    @Override
    public EventEntity dtoToEntity(EventRequestDTO sceneRequestDTO, EventEntity event)
    {
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.modelMapper.map(sceneRequestDTO, event);

        event.setActive(false);
        event.setServer(null);
        event.setSceneType(SceneType.EVENT);

        event.setPrivateScene(sceneRequestDTO.isPrivate());

        if(event.isPrivateScene()){
            String password = RandomString.make(5);
            event.setScenePassword(password);
        }
        else{
            event.setScenePassword("");
        }
        return event;

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

    @Override
    public EventResponseDTO entityToDTO(EventEntity eventEntity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(eventEntity, EventResponseDTO.class);
    }

    @Override
    public SessionEntity dtoToEntity(SessionRequestDTO sessionRequestDTO, SessionEntity sessionEntity) {
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        this.modelMapper.map(sessionRequestDTO, sessionEntity);

        //TODO yukarıdaki dtoToentity ile aynı kodlar var aşağıda. Birleşir.

        sessionEntity.setActive(false);
        sessionEntity.setServer(null);
        sessionEntity.setSceneType(SceneType.SESSION);

        sessionEntity.setPrivateScene(sessionRequestDTO.isPrivate());

        if(sessionEntity.isPrivateScene()){
            String password = RandomString.make(6); //TODO şifre uzunluğunu constanttan al
            sessionEntity.setScenePassword(password);
        }
        else{
            sessionEntity.setScenePassword("");
        }
        return sessionEntity;
    }

    @Override
    public SessionResponseDTO entityToDTO(SessionEntity session) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(session, SessionResponseDTO.class);
    }
}
