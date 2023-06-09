package com.ulascan.serverservice.service;

import com.ulascan.serverservice.dto.scene.*;
import com.ulascan.serverservice.dto.scene.session.CreateSessionResponseDTO;
import com.ulascan.serverservice.dto.scene.SceneResponseDTO;
import com.ulascan.serverservice.entity.EventEntity;
import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.service.environment.EnvironmentSceneService;
import com.ulascan.serverservice.service.event.EventSceneService;
import com.ulascan.serverservice.repository.SceneRepository;
import com.ulascan.serverservice.service.session.SessionSceneService;
import com.ulascan.serverservice.util.SceneServiceProvider;
import com.ulascan.serverservice.util.exception.BadRequestException;
import com.ulascan.serverservice.util.exception.Error;
import com.ulascan.serverservice.util.mapper.ModelConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing scenes in the server.
 * Implements the ISceneService interface.
 *
 * @author S. Ulascan Kilic
 */
@Service
@RequiredArgsConstructor
public class SceneService implements ISceneService{

    private final SceneRepository sceneRepository;

    private final ModelConverter modelConverter;

    private final EnvironmentSceneService environmentService;

    private final SessionSceneService sessionService;

    private final EventSceneService eventService;

    private final SceneServiceProvider sceneServiceProvider;

    @Override
    public Scene findFirstAvailableScene() {

        var abstractService = sceneServiceProvider.getSceneService(SceneType.EVENT);
        Scene scene = abstractService.findFirstByActiveFalse();

        if(scene != null)
            return scene;

        abstractService = sceneServiceProvider.getSceneService(SceneType.SESSION);
        scene = abstractService.findFirstByActiveFalse();

        if(scene != null)
            return scene;

        abstractService = sceneServiceProvider.getSceneService(SceneType.ENVIRONMENT);
        scene = abstractService.findFirstByActiveFalse();

        return scene;
    }

    /*public SceneResponseDTO joinWithPasswordNew(PasswordDTO passwordDTO)
    {
        Scene event = sceneRepository.findByScenePassword(passwordDTO.getPassword());

        //TODO: hataları özelleştir live değilse, active değilse vs
        if(event != null && event.isLive() && event.isActive()){
            return modelConverter.entityToDTO(event);
        }
        else {
            throw new BadRequestException(Error.EVENT_NOT_FOUND.getErrorCode(),
                    Error.EVENT_NOT_FOUND.getErrorMessage());
        }
    }*/

    /**
     * Retrieves all active scenes.
     *
     * @return List of SceneResponseDTO objects containing scene data.
     */
   /* public List<SceneResponseDTO> getAllScenes() {
        return modelConverter.mapList(sceneRepository.findByActiveTrue(),SceneResponseDTO.class);
    }*/


    /**
     * Retrieves all active scenes of a specific Unity scene name.
     *
     * @param sceneByUnityNameRequestDTO The SceneByUnityNameRequestDTO object containing the Unity scene name.
     * @return List of SceneResponseDTO objects containing scene data.
     */
    /*public List<SceneResponseDTO> getActiveScenesByUnityName(SceneByUnityNameRequestDTO sceneByUnityNameRequestDTO) {
        return modelConverter.mapList(sceneRepository
                .findByActiveTrueAndUnityScene(sceneByUnityNameRequestDTO.getUnityScene()), SceneResponseDTO.class);
    }*/

    /**
     * Deletes a scene by the server name.
     *
     * @param serverName The name of the server containing the scene to delete.
     */
   /* public void deleteSceneByServerName(String serverName) {
        serverService.deleteSceneByServerName(serverName);
    }*/


}
