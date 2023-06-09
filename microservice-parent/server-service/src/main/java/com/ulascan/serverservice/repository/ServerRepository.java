package com.ulascan.serverservice.repository;

import com.ulascan.serverservice.entity.Server;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server,Integer> {

    Server findByServerName(String serverName);

    Optional<Server> findFirstBySceneIsNull();
}
