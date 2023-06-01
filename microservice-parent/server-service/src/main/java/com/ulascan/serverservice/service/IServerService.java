package com.ulascan.serverservice.service;

import com.ulascan.serverservice.dto.ServerRequestDTO;
import com.ulascan.serverservice.dto.ServerResponseDTO;

public interface IServerService {
    ServerResponseDTO setServer(ServerRequestDTO serverRequestDTO);
    ServerRequestDTO getServerByName(String serverName);
    void deleteServerByName(String serverName);
    void deleteSceneByServerName(String serverName);
}
