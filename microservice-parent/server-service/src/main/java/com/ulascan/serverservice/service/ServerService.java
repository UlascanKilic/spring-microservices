package com.ulascan.serverservice.service;

import com.ulascan.serverservice.dto.server.ServerRequestDTO;
import com.ulascan.serverservice.dto.server.ServerResponseDTO;
import com.ulascan.serverservice.entity.EventEntity;
import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.entity.Server;
import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.enums.UnityScene;
import com.ulascan.serverservice.util.exception.BadRequestException;
import com.ulascan.serverservice.util.exception.Error;
import com.ulascan.serverservice.repository.SceneRepository;
import com.ulascan.serverservice.repository.ServerRepository;
import com.ulascan.serverservice.util.mapper.ModelConverter;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Service class for managing servers and scenes.
 *
 * @author S. Ulascan Kilic
 */
@Service
@RequiredArgsConstructor
public class ServerService implements IServerService {

    private final ServerRepository serverRepository;

    private final ISceneService sceneService;

    private final ModelConverter modelConverter;

    /**
     * Retrieves all servers.
     *
     * @return A list of ServerRequestDTO objects containing server data.
     */
    public List<ServerRequestDTO> getAllServers() {
        return modelConverter.mapList(serverRepository.findAll(), ServerRequestDTO.class);
    }

    /**
     * Sets the configuration for a server.
     * If the server exists, updates its configuration with the provided data.
     * If the server does not exist, creates a new server with the provided data.
     *
     * @param serverRequestDTO The ServerRequestDTO object containing server configuration data.
     * @return A ServerResponseDTO object containing the updated server configuration.
     */
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public ServerResponseDTO setServer(ServerRequestDTO serverRequestDTO) {

        Server server = serverRepository.findByServerName(serverRequestDTO.getServerName());
        Scene scene = sceneService.findFirstAvailableScene();
        //

        ServerResponseDTO responseDTO = ServerResponseDTO.builder().unityScene(serverRequestDTO.getUnityScene()).build();

        server = modelConverter.dtoToEntity(serverRequestDTO, Objects.requireNonNullElseGet(server, Server::new));
        //2: sahne ayrlarını yap

        if (scene != null && serverRequestDTO.getUnityScene().equals(UnityScene.IdleScene) && server.getScene() == null) {
            responseDTO.setUnityScene(scene.getUnitySceneName());
            scene.setActive(true);
            server.setScene(scene);
            scene.setServer(server);

        }
        if (!Objects.equals(responseDTO.getUnityScene(), UnityScene.IdleScene) && server.getScene() == null) {
            responseDTO.setUnityScene(UnityScene.IdleScene);
        } else if (Objects.equals(responseDTO.getUnityScene(), UnityScene.IdleScene) && server.getScene() != null) {
            responseDTO.setUnityScene(UnityScene.IdleScene);

            //TODO sahneyi sil

        }

        serverRepository.save(server);

        return responseDTO;
    }

    /**
     * Retrieves a server by its name.
     *
     * @param serverName The name of the server.
     * @return A ServerRequestDTO object containing the server data.
     * @throws BadRequestException if the server does not exist.
     */
    public ServerRequestDTO getServerByName(String serverName) {
        Server server = serverRepository.findByServerName(serverName);

        if (server == null)
            throw new BadRequestException(Error.SERVER_DOESNT_EXIST.getErrorCode(), Error.SERVER_DOESNT_EXIST.getErrorMessage());

        return modelConverter.entityToDTO(server);
    }

    /**
     * Deletes a server by its name.
     *
     * @param serverName The name of the server to delete.
     * @throws BadRequestException if the server does not exist.
     */
    @Transactional
    public void deleteServerByName(String serverName) {

        Server server = serverRepository.findByServerName(serverName);

        if (server == null)
            throw new BadRequestException(Error.SERVER_DOESNT_EXIST.getErrorCode(), Error.SERVER_DOESNT_EXIST.getErrorMessage());

        if (server.getScene() != null) {
            Scene scene = server.getScene();
            scene.setActive(false);
            scene.setServer(null);
            //TODO scene service save yaz
            //sceneRepository.save(scene); //TODO düzelt
        }

        serverRepository.delete(server);
    }

    /**
     * Deletes the scene associated with a server by server name.
     *
     * @param serverName The name of the server.
     */
    @Transactional
    public void deleteSceneByServerName(String serverName) {
        //Scene scene = sceneRepository.findByServerServerName(serverName);
        Scene scene = new Scene(); //TODO düzelt
        Server server = scene.getServer();

        if (server != null) {
            server.setScene(null);
            serverRepository.save(server);
        }

        scene.setActive(false);

        /*if(!(scene.getSceneType() == SceneType.DEFAULT))
            sceneRepository.delete(scene);*/
    }

    /**
     * Checks if there is any free server available.
     *
     * @return true if there is a free server, false otherwise.
     */
    public boolean findFreeServer() {
        return serverRepository.findFirstBySceneIsNull().isPresent();
    }
}

