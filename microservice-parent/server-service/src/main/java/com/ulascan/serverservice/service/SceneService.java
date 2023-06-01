package com.ulascan.serverservice.service;

import com.ulascan.serverservice.dto.*;
import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.enums.DefaultUnityScenes;
import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.enums.UnityScene;
import com.ulascan.serverservice.exception.BadRequestException;
import com.ulascan.serverservice.exception.Error;
import com.ulascan.serverservice.repository.SceneRepository;
import com.ulascan.serverservice.utils.Constants;
import com.ulascan.serverservice.utils.Mapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Service class for managing scenes in the server.
 * Implements the ISceneService interface.
 *
 * @author S. Ulascan Kilic
 */
@Service
@RequiredArgsConstructor
public class SceneService implements ISceneService {

    private final SceneRepository sceneRepository;

    private final ServerService serverService;

    private final Mapper mapper;

    /**
     * Method executed after the bean initialization.
     * Checks if default scenes exist in the repository, and if not, creates them.
     */
    @PostConstruct
    private void postConstruct() {
        List<UnityScene> sceneNameList = sceneRepository.findAllUnitySceneNames();

        for(UnityScene sceneName : DefaultUnityScenes.DEFAULT_UNITY_SCENES.getScenes() ){
            if(!sceneNameList.contains(sceneName)){
                //open scene
                sceneRepository.save(Scene.builder()
                        .unityScene(sceneName)
                        .sceneName(Constants.getDefaultSceneName())
                        .scenePassword(Constants.getDefaultPassword())
                        .hostEmail(Constants.getDefaultEmail())
                        .hostFirstName(Constants.getDefaultFirstName())
                        .hostLastName(Constants.getDefaultLastName())
                        .privateScene(false)
                        .sceneType(SceneType.DEFAULT)
                        .maxUserCapacity(Constants.getMaxUserCount())
                        .active(false)
                        .build());
            }
        }
    }

    /**
     * Retrieves all active scenes.
     *
     * @return List of SceneResponseDTO objects containing scene data.
     */
    public List<SceneResponseDTO> getAllScenes() {
        return mapper.mapList(sceneRepository.findByActiveTrue(),SceneResponseDTO.class);
    }

    /**
     * Starts a scene by creating a new scene in the repository with the provided scene data.
     *
     * @param sceneRequestDTO The SceneRequestDTO object containing the scene data.
     * @return The StartSceneResponseDTO object containing the scene password.
     */
    @Transactional
    public StartSceneResponseDTO startScene(SceneRequestDTO sceneRequestDTO) {

        filterChainForStartScene(sceneRequestDTO);

        return StartSceneResponseDTO
                .builder()
                    .scenePassword(sceneRepository
                    .save(mapper
                            .dtoToEntity(sceneRequestDTO, Scene.builder().build()))
                    .getScenePassword())
                .build();

    }

    /**
     * Joins a scene by verifying the scene name and password.
     *
     * @param joinSceneDTO The JoinSceneDTO object containing the scene name and password.
     * @throws BadRequestException if the scene is not found or the password doesn't match.
     */
    public void joinScene(JoinSceneDTO joinSceneDTO) {
        Scene scene = sceneRepository.getSceneBySceneName(joinSceneDTO.getSceneName());

        if(scene == null){
            throw new BadRequestException(Error.SCENE_NOT_FOUND.getErrorCode(),
                    Error.SCENE_NOT_FOUND.getErrorMessage());
        }
        else if(!Objects.equals(scene.getScenePassword(), joinSceneDTO.getPassword()))
        {
            throw new BadRequestException(Error.SCENE_PASSWORD_DOESNT_MATCH.getErrorCode(),
                    Error.SCENE_PASSWORD_DOESNT_MATCH.getErrorMessage());
        }

    }

    /**
     * Retrieves all active scenes of a specific scene type.
     *
     * @param dto The SceneByTypeRequestDTO object containing the scene type.
     * @return List of SceneResponseDTO objects containing scene data.
     */
    public List<SceneResponseDTO> getActiveScenesByType(SceneByTypeRequestDTO dto) {
        return mapper.mapList(sceneRepository.findByActiveTrueAndSceneType(dto.getSceneType()),SceneResponseDTO.class);
    }

    /**
     * Retrieves all scenes hosted by a specific user.
     *
     * @param dto The SceneByUserRequestDTO object containing the user's email.
     * @return List of SceneResponseDTO objects containing scene data.
     */
    public List<SceneResponseDTO> getScenesByUser(SceneByUserRequestDTO dto)
    {
        return mapper.mapList(sceneRepository.findAllByHostEmail(dto.getHostEmail()),SceneResponseDTO.class);
    }

    /**
     * Retrieves all active scenes of a specific Unity scene name.
     *
     * @param sceneByUnityNameRequestDTO The SceneByUnityNameRequestDTO object containing the Unity scene name.
     * @return List of SceneResponseDTO objects containing scene data.
     */
    public List<SceneResponseDTO> getActiveScenesByUnityName(SceneByUnityNameRequestDTO sceneByUnityNameRequestDTO) {
        return mapper.mapList(sceneRepository
                .findByActiveTrueAndUnityScene(sceneByUnityNameRequestDTO.getUnityScene()), SceneResponseDTO.class);
    }

    /**
     * Deletes a scene by the server name.
     *
     * @param serverName The name of the server containing the scene to delete.
     */
    public void deleteSceneByServerName(String serverName) {
        serverService.deleteSceneByServerName(serverName);
    }

    private boolean checkIfNameExists(String name)
    {
        return sceneRepository.findBySceneName(name).isPresent();
    }
    private boolean checkIfHostExists(String hostEmail){
        return sceneRepository.findByHostEmail(hostEmail).isPresent();
    }

    /**
     * Filters the chain of conditions before starting a scene.
     * Checks if there is a free server, if the scene name already exists, and if the host already exists.
     *
     * @param sceneRequestDTO The SceneRequestDTO object containing the scene data.
     * @throws BadRequestException if no free server is found, the scene name already exists, or the host already exists.
     */
    private void filterChainForStartScene(SceneRequestDTO sceneRequestDTO)
    {
        boolean isAnyAvailableServer = serverService.findFreeServer();

        if(!isAnyAvailableServer){
            throw new BadRequestException(Error.NO_FREE_SERVER_FOUND.getErrorCode(),
                    Error.NO_FREE_SERVER_FOUND.getErrorMessage());
        }
        else if(checkIfNameExists(sceneRequestDTO.getSceneName()))
        {
            throw new BadRequestException(Error.DUPLICATE_SCENE_NAME.getErrorCode(),
                    Error.DUPLICATE_SCENE_NAME.getErrorMessage());
        }
        else if(checkIfHostExists(sceneRequestDTO.getHostEmail()))
        {
            throw new BadRequestException(Error.HOST_ALREADY_EXISTS.getErrorCode(),
                    Error.HOST_ALREADY_EXISTS.getErrorMessage());
        }
    }
}
