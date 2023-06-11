package com.ulascan.serverservice.service;

import com.ulascan.serverservice.dto.scene.*;
import com.ulascan.serverservice.dto.scene.SceneResponseDTO;
import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.repository.ISceneRepository;
import com.ulascan.serverservice.repository.IServerRepository;
import org.modelmapper.ModelMapper;

import java.util.List;

public abstract class AbstractSceneService {

    protected ModelMapper mapper = new ModelMapper();

    protected IServerRepository serverRepository;
    protected ISceneRepository sceneRepository;

    public AbstractSceneService(IServerRepository serverRepository,
                                ISceneRepository sceneRepository) {
        this.serverRepository = serverRepository;
        this.sceneRepository = sceneRepository;
    }

    public abstract List<SceneResponseDTO> getAll();

    public abstract CreateResponseDTO create(SceneRequestDTO sceneRequestDTO);

    public abstract SceneResponseDTO getByName(NameDTO nameDTO);

    public abstract Scene getSceneByName(String name);

    public abstract SceneResponseDTO joinWithPassword(PasswordDTO passwordDTO);


    public abstract Scene getScenePassword(String password);

    public abstract Scene findFirstByActiveFalse();

    public abstract void join(JoinDTO joinDTO);

    public abstract void validateScene(SceneRequestDTO sceneRequestDTO);

    public abstract void delete(Scene scene);

    public abstract void setSceneFree(Scene scene);
/*
    public abstract SceneResponseDTO entityToDTO(Scene scene);

*/

    /**
     * Checks if there is any free server available.
     *
     * @return true if there is a free server, false otherwise.
     */
    public boolean findFreeServer() {
        return serverRepository.findFirstBySceneIsNull().isEmpty() &&
                sceneRepository.count() <= serverRepository.count();
    }
}
