package com.ulascan.serverservice.repository;

import com.ulascan.serverservice.entity.EnvironmentEntity;
import com.ulascan.serverservice.enums.UnityScene;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IEnvironmentRepository extends JpaRepository<EnvironmentEntity,Integer> {

    List<EnvironmentEntity> findByActiveTrue();

    @Query(value = "SELECT req.unityScene FROM Scene req")
    List<UnityScene> findAllUnityScenes();

    EnvironmentEntity findFirstByActiveFalse();
}
