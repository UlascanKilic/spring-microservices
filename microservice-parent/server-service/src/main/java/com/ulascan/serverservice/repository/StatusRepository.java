package com.ulascan.serverservice.repository;

import com.ulascan.serverservice.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status,Integer> {

    public Status findByServerName(String serverName);
}
