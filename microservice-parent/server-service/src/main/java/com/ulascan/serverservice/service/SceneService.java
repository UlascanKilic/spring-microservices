package com.ulascan.serverservice.service;

import com.ulascan.serverservice.dto.*;
import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.entity.Server;
import com.ulascan.serverservice.enums.DefaultUnityScenes;
import com.ulascan.serverservice.enums.SceneType;
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
public class SceneService {

    private final SceneRepository sceneRepository;

    private final ServerService serverService;

    private final Mapper mapper;


    @Value("${entity.max.user.count}")
    private Integer maxUserCount;

    @PostConstruct
    private void postConstruct() {
        List<String> sceneNameList = sceneRepository.findAllUnitySceneNames();
        for(DefaultUnityScenes sceneName : DefaultUnityScenes.values() ){
            if(!sceneNameList.contains(sceneName.name())){
                //open scene
                sceneRepository.save(Scene.builder()
                        .unitySceneName(sceneName.name())
                        .sceneName("ORTABAHCE")
                        .scenePassword("")
                        .hostEmail("ytustarverse@gmail.com")
                        .hostFirstName("YTU")
                        .hostLastName("Starverse")
                        .isPrivateScene(false)
                        .sceneType(SceneType.DEFAULT)
                        .maxUserCapacity(maxUserCount)
                        .active(false)
                        .build());
            }
        }
    }

    public List<SceneResponseDTO> getAllScenes() {
        return mapper.mapList(sceneRepository.findByActiveTrue(),SceneResponseDTO.class);
    }

    @Transactional
    public void startScene(SceneRequestDTO sceneRequestDTO) {
        //TODO aynı isimden iki tane scene olamaz! (sceneName)
        //todo bir email sadece bir tane sahne açabilir(default hariç)
        //todo scene passwor'u şifreleyip koy

        boolean isAnyAvailableServer = serverService.findFreeServer();

        if(!isAnyAvailableServer){
            throw new BadRequestException(Error.NO_FREE_SERVER_FOUND.getErrorCode(),
                    Error.NO_FREE_SERVER_FOUND.getErrorMessage());
        }
        if(checkIfNameExists(sceneRequestDTO.getSceneName()))
        {
            throw new BadRequestException(Error.DUPLICATE_SCENE_NAME.getErrorCode(),
                    Error.DUPLICATE_SCENE_NAME.getErrorMessage());
        }

        sceneRepository.save(mapper.dtoToEntity(sceneRequestDTO, Scene.builder().build()));

    }

    public void loginScene(LoginSceneDTO loginSceneDTO) {
        Scene scene = sceneRepository.getSceneBySceneName(loginSceneDTO.getSceneName());
        // todo scene name null ise? repodan null ref gelir?

        if(scene == null){
            throw new BadRequestException(Error.SCENE_NOT_FOUND.getErrorCode(),
                    Error.SCENE_NOT_FOUND.getErrorMessage());
        }
        else if(!Objects.equals(scene.getScenePassword(), loginSceneDTO.getPassword()))
        {
            throw new BadRequestException(Error.SCENE_PASSWORD_DOESNT_MATCH.getErrorCode(),
                    Error.SCENE_PASSWORD_DOESNT_MATCH.getErrorMessage());
        }

        //todo scene active değilse?

        //todo hangi kullanıcı hangi sunucuda bilgisini db de tutacak mıyız?
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
}
