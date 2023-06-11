package com.ulascan.serverservice.repository;

import com.ulascan.serverservice.entity.Scene;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISceneRepository extends JpaRepository<Scene,Integer> {

    Scene findFirstByActiveFalseOrderBySceneTypeAsc();

    Scene findByServerServerName(String serverName);

    Scene findByName(String sceneName);
}
