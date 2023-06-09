package com.ulascan.serverservice.repository;

import com.ulascan.serverservice.dto.scene.event.EventResponseDTO;
import com.ulascan.serverservice.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEventRepository extends JpaRepository<EventEntity,Integer> {

    List<EventEntity> findByActiveTrue();

    EventEntity findByEventName(String eventName);

    EventEntity findFirstByActiveFalseAndLiveTrue();

    EventEntity findByScenePassword(String scenePassword);
}
