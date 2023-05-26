package com.ulascan.launcherservice.repository;

import com.ulascan.launcherservice.entity.Launcher;
import com.ulascan.launcherservice.entity.LauncherText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LauncherTextRepository extends JpaRepository<LauncherText, Integer> {
    LauncherText findFirstByOrderById();
}
