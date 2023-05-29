package com.ulascan.serverservice.repository;

import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.entity.Server;
import com.ulascan.serverservice.view.UnitySceneNameView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SceneRepository extends JpaRepository<Scene,Integer> {

    List<Scene> findByActiveTrue();

    Scene findByServerServerName(String serverName);

    Scene findFirstByActiveFalse();

    List<UnitySceneNameView> findAllByUnitySceneName(String unitySceneName);

    @Query(value = "SELECT req.unitySceneName FROM Scene req")
    List<String> findAllUnitySceneNames();
}
