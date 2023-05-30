package com.ulascan.serverservice.service;

import com.ulascan.serverservice.dto.ServerRequestDTO;
import com.ulascan.serverservice.dto.ServerResponseDTO;
import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.entity.Server;
import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.enums.UnityScene;
import com.ulascan.serverservice.exception.BadRequestException;
import com.ulascan.serverservice.exception.Error;
import com.ulascan.serverservice.repository.SceneRepository;
import com.ulascan.serverservice.repository.ServerRepository;
import com.ulascan.serverservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ServerService {

    private final ServerRepository serverRepository;

    private final SceneRepository sceneRepository;

    private final Mapper mapper;

    public List<ServerRequestDTO> getAllServers() {
        return mapper.mapList(serverRepository.findAll(), ServerRequestDTO.class);
    }

    @Transactional
    public ServerResponseDTO setServer(ServerRequestDTO serverRequestDTO) {

        Server server = serverRepository.findByServerName(serverRequestDTO.getServerName());
        Scene scene = sceneRepository.findFirstByActiveFalseOrderBySceneTypeAsc();
        ServerResponseDTO responseDTO = ServerResponseDTO.builder().unityScene(serverRequestDTO.getUnityScene()).build();

        server = mapper.dtoToEntity(serverRequestDTO, Objects.requireNonNullElseGet(server, Server::new));
        //2: sahne ayrlarını yap

        if(scene != null && serverRequestDTO.getUnityScene().equals(UnityScene.IDLE_SCENE) && server.getScene() == null)
        {
            responseDTO.setUnityScene(scene.getUnityScene());
            scene.setActive(true);
            server.setScene(scene);
            scene.setServer(server);

        }
        if(!Objects.equals(responseDTO.getUnityScene(), UnityScene.IDLE_SCENE) && server.getScene() == null)
        {
            responseDTO.setUnityScene(UnityScene.IDLE_SCENE);
        }
        else if(Objects.equals(responseDTO.getUnityScene(), UnityScene.IDLE_SCENE) && server.getScene() != null)
        {
            responseDTO.setUnityScene(UnityScene.IDLE_SCENE);

            //TODO sahneyi sil

        }

        serverRepository.save(server);

        return responseDTO;
    }

    /*@Transactional
    public ServerResponseDTO setServerCount(ServerCountDTO serverCountDTO) {
        Server server = serverRepository.findByServerName(serverCountDTO.getServerName());


        if(server == null)
        {
            server = Server.builder()
                    .serverName(serverCountDTO.getServerName())
                    .userCount(serverCountDTO.getUserCount())
                    .isUp(defaultIsUp)
                    .build();
        }
        else {
            server = mapper.dtoToEntity(serverCountDTO, server);
        }
        serverRepository.save(server);
        return null;
    }*/

    public ServerRequestDTO getServerByName(String serverName) {
        Server server = serverRepository.findByServerName(serverName);

        if(server == null) throw new BadRequestException(Error.SERVER_DOESNT_EXIST.getErrorCode(), Error.SERVER_DOESNT_EXIST.getErrorMessage());

        return mapper.entityToDTO(server) ;
    }

    @Transactional
    public void deleteServerByName(String serverName) {

        Server server = serverRepository.findByServerName(serverName);

        if(server == null) throw new BadRequestException(Error.SERVER_DOESNT_EXIST.getErrorCode(), Error.SERVER_DOESNT_EXIST.getErrorMessage());
        //TODO deleteSceneByServerName ile birelştirip tek fonksiyona indir
        if(server.getScene() != null)
        {
            Scene scene = server.getScene();
            scene.setActive(false);
            scene.setServer(null);
            sceneRepository.save(scene);
        }

        serverRepository.delete(server);
    }

    @Transactional
    public void deleteSceneByServerName(String serverName) {
        Scene scene = sceneRepository.findByServerServerName(serverName);
        Server server = scene.getServer();
        server.setScene(null);
        scene.setActive(false);

        if(!(scene.getSceneType() == SceneType.DEFAULT))
            sceneRepository.delete(scene);
    }

    public boolean findFreeServer() {
        return serverRepository.findFirstBySceneIsNull().isPresent();
    }
}
