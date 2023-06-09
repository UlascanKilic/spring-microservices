package com.ulascan.serverservice.service;

import com.ulascan.serverservice.dto.server.ServerRequestDTO;
import com.ulascan.serverservice.dto.server.ServerResponseDTO;

public interface IServerService {
    ServerResponseDTO setServer(ServerRequestDTO serverRequestDTO);
    ServerRequestDTO getServerByName(String serverName);
    void deleteServerByName(String serverName);
    void deleteSceneByServerName(String serverName);
}
