package com.ulascan.serverservice.service;

import com.ulascan.serverservice.dto.ServerCountDTO;
import com.ulascan.serverservice.dto.ServerDTO;
import com.ulascan.serverservice.dto.ServerResponseDTO;
import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.entity.Server;
import com.ulascan.serverservice.enums.DefaultUnityScenes;
import com.ulascan.serverservice.enums.UnitySceneName;
import com.ulascan.serverservice.exception.BadRequestException;
import com.ulascan.serverservice.exception.Error;
import com.ulascan.serverservice.repository.SceneRepository;
import com.ulascan.serverservice.repository.ServerRepository;
import com.ulascan.serverservice.utils.Mapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${entity.max.user.count}")
    private Integer maxUserCount;

    @Value("${entity.default.isup}")
    private boolean defaultIsUp;


    public List<ServerDTO> getAllServers() {
        return mapper.mapList(serverRepository.findAll(), ServerDTO.class);
    }

    @Transactional
    public ServerResponseDTO setServer(ServerDTO serverDTO) {

        Server server = serverRepository.findByServerName(serverDTO.getServerName());
        Scene scene = sceneRepository.findFirstByActiveFalse();
        ServerResponseDTO responseDTO = ServerResponseDTO.builder().unitySceneName(serverDTO.getUnitySceneName()).build();

        server = mapper.dtoToEntity(serverDTO, Objects.requireNonNullElseGet(server, Server::new));
        //2: sahne ayrlarını yap

        //TODO öncelik default scene varsa onun
        if(scene != null && serverDTO.getUnitySceneName().equals(UnitySceneName.IDLE_SCENE.getSceneName()) && server.getScene() == null)
        {
            responseDTO.setUnitySceneName(scene.getUnitySceneName());
            scene.setActive(true);
            server.setScene(scene);
            scene.setServer(server);

        }
        if(!Objects.equals(serverDTO.getUnitySceneName(), UnitySceneName.IDLE_SCENE.name()) && server.getScene() == null)
        {
            //sen idle'sın idle dön
            responseDTO.setUnitySceneName(UnitySceneName.IDLE_SCENE.getSceneName());
        }
        else if(Objects.equals(serverDTO.getUnitySceneName(), UnitySceneName.IDLE_SCENE.name()) && server.getScene() != null)
        {
            responseDTO.setUnitySceneName(UnitySceneName.IDLE_SCENE.getSceneName());

            //sahneyi sil
            //sen idle'sın idle dön
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

    public ServerDTO getServerByName(String serverName) {
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
        //TODO eğer silinen scene isDefaultScene ise delete yapma, active ini sil
        server.setScene(null);
        scene.setActive(false);

        if(!scene.isDefaultScene())
            sceneRepository.delete(scene);
    }
}
