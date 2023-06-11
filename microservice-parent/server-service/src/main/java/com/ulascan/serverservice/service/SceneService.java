package com.ulascan.serverservice.service;

import com.ulascan.serverservice.dto.scene.NameDTO;
import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.entity.Server;
import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.repository.ISceneRepository;
import com.ulascan.serverservice.util.SceneServiceProvider;
import com.ulascan.serverservice.util.exception.BadRequestException;
import com.ulascan.serverservice.util.exception.Error;
import com.ulascan.serverservice.util.mapper.ModelConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing scenes in the server.
 * Implements the ISceneService interface.
 *
 * @author S. Ulascan Kilic
 */
@Service
@RequiredArgsConstructor
public class SceneService implements ISceneService{

    private final ISceneRepository sceneRepository;

    private final ModelConverter modelConverter;
    private final SceneServiceProvider sceneServiceProvider;

    @Override
    public Scene findFirstAvailableScene() {

        SceneType[] sceneTypes = {SceneType.EVENT, SceneType.SESSION, SceneType.ENVIRONMENT};

        for (SceneType sceneType : sceneTypes) {
            var abstractService = sceneServiceProvider.getSceneService(sceneType);
            Scene scene = abstractService.findFirstByActiveFalse();

            if (scene != null) {
                return scene;
            }
        }

        return null;
    }

    @Override
    @Transactional
    public void setSceneFree(Scene scene) {
        var abstractService = sceneServiceProvider.getSceneService(scene.getSceneType());
        abstractService.setSceneFree(scene);
    }

    @Override
    @Transactional
    public void delete(NameDTO nameDTO) {

        Scene scene = sceneRepository.findByName(nameDTO.getName());
        if(scene != null)
        {
            var abstractService = sceneServiceProvider.getSceneService(scene.getSceneType());
            abstractService.delete(scene);
        }
        else {
            throw new BadRequestException(Error.SCENE_NOT_FOUND.getErrorCode(), Error.SCENE_NOT_FOUND.getErrorMessage());
        }
    }

}
