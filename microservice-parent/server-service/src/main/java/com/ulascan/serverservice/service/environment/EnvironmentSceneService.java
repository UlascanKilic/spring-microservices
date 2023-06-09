package com.ulascan.serverservice.service.environment;

import com.ulascan.serverservice.dto.scene.*;
import com.ulascan.serverservice.dto.scene.environment.EnvironmentResponseDTO;
import com.ulascan.serverservice.dto.scene.SceneResponseDTO;
import com.ulascan.serverservice.entity.EnvironmentEntity;
import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.enums.DefaultUnityScenes;
import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.enums.UnityScene;
import com.ulascan.serverservice.repository.IEnvironmentRepository;
import com.ulascan.serverservice.service.AbstractSceneService;
import com.ulascan.serverservice.util.Constants;
import com.ulascan.serverservice.util.mapper.ModelConverter;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnvironmentSceneService extends AbstractSceneService {

    private final IEnvironmentRepository repository;
    private final ModelConverter modelConverter;

    @PostConstruct
    public void postConstruct() {
        List<UnityScene> sceneNameList = repository.findAllUnitySceneNames();

        for(UnityScene sceneName : DefaultUnityScenes.DEFAULT_UNITY_SCENES.getScenes() ){
            if(!sceneNameList.contains(sceneName)){
                //open scene
                EnvironmentEntity environment = new EnvironmentEntity();
                environment.setEnvironmentName("Ortabahce");
                environment.setDescription("Baharin vazgecilmezi!");
                environment.setMaxUserCapacity(60);
                environment.setActive(false);
                environment.setServer(null);
                environment.setSceneType(SceneType.ENVIRONMENT);
                environment.setUnitySceneName(sceneName);

                repository.save(environment);
            }
        }
    }

    public List<EnvironmentResponseDTO> getAllEnvironments() {
        return modelConverter.mapList(repository.findByActiveTrue(), EnvironmentResponseDTO.class);
    }

    @Override
    public List<SceneResponseDTO> getAll() {
        return null;
    }

    @Override
    public CreateResponseDTO create(SceneRequestDTO sceneRequestDTO) {
        return null;
    }

    @Override
    public SceneResponseDTO getByName(NameDTO nameDTO) {
        return null;
    }

    @Override
    public Scene getSceneByName(String name) {
        return null;
    }

    @Override
    public SceneResponseDTO joinWithPassword(PasswordDTO passwordDTO) {
        return null;
    }

    @Override
    public Scene getScenePassword(String password) {
        return null;
    }

    @Override
    public EnvironmentEntity findFirstByActiveFalse() {
        return repository.findFirstByActiveFalse();
    }

    @Override
    public void join(JoinDTO joinDTO) {

    }

    @Override
    public void validateScene(SceneRequestDTO sceneRequestDTO) {

    }
}
