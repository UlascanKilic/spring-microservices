package com.ulascan.serverservice.service;

import com.ulascan.serverservice.dto.scene.*;
import com.ulascan.serverservice.dto.scene.session.CreateSessionResponseDTO;
import com.ulascan.serverservice.dto.scene.SceneResponseDTO;
import com.ulascan.serverservice.entity.Scene;

import java.util.List;

public interface ISceneService {
    Scene findFirstAvailableScene();

    void setSceneFree(Scene scene);

    void delete(NameDTO nameDTO);
}
