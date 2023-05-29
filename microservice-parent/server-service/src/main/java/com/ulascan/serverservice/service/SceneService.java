package com.ulascan.serverservice.service;

import com.ulascan.serverservice.dto.SceneResponseDTO;
import com.ulascan.serverservice.dto.StartSceneDTO;
import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.entity.Server;
import com.ulascan.serverservice.enums.DefaultUnityScenes;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SceneService {

    private final SceneRepository sceneRepository;

    private final ServerRepository serverRepository;

    private final Mapper mapper;


    @Value("${entity.max.user.count}")
    private Integer maxUserCount;

    @PostConstruct
    private void postConstruct() {
        System.out.println("selamke");
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
                        .isDefaultScene(true)
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
    public void startScene(StartSceneDTO startSceneDTO) {
        //TODO aynı isimden iki tane scene olamaz! (sceneName)
        //todo bir email sadece bir tane sahne açabilir(default hariç)
        Server server = serverRepository.findFirstBySceneIsNull();

        if(server == null){
            throw new BadRequestException(Error.NO_FREE_SERVER_FOUND.getErrorCode(),
                    Error.NO_FREE_SERVER_FOUND.getErrorMessage());
        }

        sceneRepository.save(mapper.dtoToEntity(startSceneDTO, Scene.builder().build()));

    }


    //todo kullanıcıya ait olan sessionlar (kapalı ve açık)
}
