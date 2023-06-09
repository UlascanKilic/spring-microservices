package com.ulascan.serverservice.service;

import com.ulascan.serverservice.dto.scene.*;
import com.ulascan.serverservice.dto.scene.SceneResponseDTO;
import com.ulascan.serverservice.entity.Scene;
import org.modelmapper.ModelMapper;

import java.util.List;

public abstract class AbstractSceneService {

    protected ModelMapper mapper = new ModelMapper();


    public abstract List<SceneResponseDTO> getAll();

    public abstract CreateResponseDTO create(SceneRequestDTO sceneRequestDTO);

    public abstract SceneResponseDTO getByName(NameDTO nameDTO);

    public abstract Scene getSceneByName(String name);

    public abstract SceneResponseDTO joinWithPassword(PasswordDTO passwordDTO);


    public abstract Scene getScenePassword(String password);

    public abstract Scene findFirstByActiveFalse();

    public abstract void join(JoinDTO joinDTO);

    public abstract void validateScene(SceneRequestDTO sceneRequestDTO);
/*
    public abstract SceneResponseDTO entityToDTO(Scene scene);

*/
}
