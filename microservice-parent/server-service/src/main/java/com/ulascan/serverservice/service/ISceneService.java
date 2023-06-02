package com.ulascan.serverservice.service;

import com.ulascan.serverservice.dto.*;

import java.util.List;

public interface ISceneService {
    List<SceneResponseDTO> getAllScenes();
    StartSceneResponseDTO startScene(SceneRequestDTO sceneRequestDTO);
    void joinScene(JoinSceneDTO joinSceneDTO);
    List<SceneResponseDTO> getActiveScenesByType(SceneByTypeRequestDTO dto);
    List<SceneResponseDTO> getScenesByUser(SceneByUserRequestDTO dto);
    List<SceneResponseDTO> getActiveScenesByUnityName(SceneByUnityNameRequestDTO sceneByUnityNameRequestDTO);
}
