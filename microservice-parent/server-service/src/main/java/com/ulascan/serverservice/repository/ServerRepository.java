package com.ulascan.serverservice.repository;

import com.ulascan.serverservice.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server,Integer> {

    Server findByServerName(String serverName);

    Server findFirstBySceneIsNull();
}
