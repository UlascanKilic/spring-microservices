package com.ulascan.serverservice.repository;

import com.ulascan.serverservice.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISessionRepository extends JpaRepository<SessionEntity,Integer> {

    SessionEntity findByName(String sessionName);

    SessionEntity findByScenePassword(String password);

    SessionEntity findFirstByActiveFalse();

    List<SessionEntity> findByActiveTrue();
}
