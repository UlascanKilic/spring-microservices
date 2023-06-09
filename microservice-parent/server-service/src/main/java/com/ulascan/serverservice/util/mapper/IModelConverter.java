package com.ulascan.serverservice.util.mapper;

import com.ulascan.serverservice.dto.scene.event.EventRequestDTO;
import com.ulascan.serverservice.dto.scene.event.EventResponseDTO;
import com.ulascan.serverservice.dto.scene.session.SessionRequestDTO;
import com.ulascan.serverservice.dto.scene.session.SessionResponseDTO;
import com.ulascan.serverservice.entity.EventEntity;
import com.ulascan.serverservice.entity.SessionEntity;

public interface IModelConverter {
    EventEntity dtoToEntity(EventRequestDTO sceneRequestDTO, EventEntity event);

    EventResponseDTO entityToDTO(EventEntity eventEntity);

    SessionEntity dtoToEntity(SessionRequestDTO sessionRequestDTO, SessionEntity sessionEntity);

    SessionResponseDTO entityToDTO(SessionEntity session);
}
