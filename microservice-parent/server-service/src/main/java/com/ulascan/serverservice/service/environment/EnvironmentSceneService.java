package com.ulascan.serverservice.service.environment;

import com.ulascan.serverservice.dto.scene.*;
import com.ulascan.serverservice.dto.scene.environment.EnvironmentResponseDTO;
import com.ulascan.serverservice.dto.scene.SceneResponseDTO;
import com.ulascan.serverservice.entity.*;
import com.ulascan.serverservice.enums.DefaultUnityScenes;
import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.enums.UnityScene;
import com.ulascan.serverservice.repository.IEnvironmentRepository;
import com.ulascan.serverservice.repository.ISceneRepository;
import com.ulascan.serverservice.repository.IServerRepository;
import com.ulascan.serverservice.service.AbstractSceneService;
import com.ulascan.serverservice.util.mapper.ModelConverter;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentSceneService extends AbstractSceneService {

    private final IEnvironmentRepository repository;
    private final ModelConverter modelConverter;

    @Autowired
    public EnvironmentSceneService(IEnvironmentRepository repository,
                                   ModelConverter modelConverter,
                                   IServerRepository serverRepository,
                                   ISceneRepository sceneRepository){
        super(serverRepository,sceneRepository);
        this.repository = repository;
        this.modelConverter = modelConverter;
    }

    @PostConstruct
    public void postConstruct() {
        List<UnityScene> sceneNameList = repository.findAllUnityScenes();

        for(UnityScene sceneName : DefaultUnityScenes.DEFAULT_UNITY_SCENES.getScenes() ){
            if(!sceneNameList.contains(sceneName)){
                //open scene
                EnvironmentEntity environment = new EnvironmentEntity();
                environment.setName("Ortabahce");
                environment.setDescription("Baharin vazgecilmezi!");
                environment.setMaxUserCapacity(60);
                environment.setActive(false);
                environment.setServer(null);
                environment.setSceneType(SceneType.ENVIRONMENT);
                environment.setUnityScene(sceneName);

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

    @Override
    public void delete(Scene scene) {
        EnvironmentEntity environmentEntity = (EnvironmentEntity) scene;
        Server server = environmentEntity.getServer();

        if (server != null) {
            server.setScene(null);
            serverRepository.save(server);
        }

        environmentEntity.setActive(false);
        //repository.delete(environmentEntity);
    }

    @Override
    public void setSceneFree(Scene scene) {
        EnvironmentEntity environmentEntity = (EnvironmentEntity) scene;
        environmentEntity.setActive(false);
        environmentEntity.setServer(null);
        repository.save(environmentEntity);
    }
}
