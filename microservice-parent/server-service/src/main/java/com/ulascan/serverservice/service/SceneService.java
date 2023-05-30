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

@Service
@RequiredArgsConstructor
public class SceneService {

    private final SceneRepository sceneRepository;

    private final ServerService serverService;

    private final Mapper mapper;

    @PostConstruct
    private void postConstruct() {
        List<UnityScene> sceneNameList = sceneRepository.findAllUnitySceneNames();

        for(UnityScene sceneName : DefaultUnityScenes.DEFAULT_UNITY_SCENES.getScenes() ){
            if(!sceneNameList.contains(sceneName)){
                //open scene
                sceneRepository.save(Scene.builder()
                        .unityScene(sceneName)
                        .sceneName("ORTABAHCE")
                        .scenePassword(Constants.DEFAULT_PASSWORD)
                        .hostEmail("ytustarverse@gmail.com")
                        .hostFirstName("YTU")
                        .hostLastName("Starverse")
                        .privateScene(false)
                        .sceneType(SceneType.DEFAULT)
                        .maxUserCapacity(Constants.MAX_USER_COUNT)
                        .active(false)
                        .build());
            }
        }
    }

    public List<SceneResponseDTO> getAllScenes() {
        return mapper.mapList(sceneRepository.findByActiveTrue(),SceneResponseDTO.class);
    }

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

    public List<SceneResponseDTO> getActiveScenesByType(SceneByTypeRequestDTO dto) {
        return mapper.mapList(sceneRepository.findByActiveTrueAndSceneType(dto.getSceneType()),SceneResponseDTO.class);
    }

    public List<SceneResponseDTO> getScenesByUser(SceneByUserRequestDTO dto)
    {
        return mapper.mapList(sceneRepository.findAllByHostEmail(dto.getHostEmail()),SceneResponseDTO.class);
    }

    private boolean checkIfNameExists(String name)
    {
        return sceneRepository.findBySceneName(name).isPresent();
    }
    private boolean checkIfHostExists(String hostEmail){
        return sceneRepository.findByHostEmail(hostEmail).isPresent();
    }

    public void deleteSceneByServerName(String serverName) {
        serverService.deleteSceneByServerName(serverName);
    }

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
