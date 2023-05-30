package com.ulascan.serverservice.repository;

import com.ulascan.serverservice.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server,Integer> {

    Server findByServerName(String serverName);

    Optional<Server> findFirstBySceneIsNull();
}
