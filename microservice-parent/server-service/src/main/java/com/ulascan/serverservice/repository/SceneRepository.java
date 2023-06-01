package com.ulascan.serverservice.repository;

import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.entity.Server;
import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.enums.UnityScene;
import com.ulascan.serverservice.view.UnitySceneNameView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SceneRepository extends JpaRepository<Scene,Integer> {

    List<Scene> findByActiveTrue();

    List<Scene> findByActiveTrueAndSceneType(SceneType sceneType);

    List<Scene> findByActiveTrueAndUnityScene(UnityScene unityScene);

    List<Scene> findAllByHostEmail(String hostEmail);



    Scene findByServerServerName(String serverName);

    Scene findFirstByActiveFalseOrderBySceneTypeAsc();

    Scene getSceneBySceneName(String sceneName);
    @Query(value = "SELECT req.unityScene FROM Scene req")
    List<UnityScene> findAllUnitySceneNames();

    Optional<Scene> findBySceneName(String sceneName);

    Optional<Scene> findByHostEmail(String hostEmail);

}
