package com.ulascan.launcherservice.repository;

import com.ulascan.launcherservice.entity.Launcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LauncherRepository extends JpaRepository<Launcher, Integer> {
    Launcher findFirstByOrderById();
}
