package com.ulascan.serverservice.controller;

import com.ulascan.serverservice.dto.server.ServerRequestDTO;
import com.ulascan.serverservice.dto.server.ServerResponseDTO;
import com.ulascan.serverservice.service.ServerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/server")
@RequiredArgsConstructor
public class ServerController {
    private final ServerService serverService;

    @GetMapping
    public ResponseEntity<List<ServerRequestDTO>> getAllServerStatus(){
        return ResponseEntity.ok(serverService.getAllServers());
    }

    @PostMapping
    public ResponseEntity<ServerResponseDTO> setServer(@Valid @RequestBody ServerRequestDTO serverRequestDTO)
    {
         return ResponseEntity.ok(serverService.setServer(serverRequestDTO));
    }

    @GetMapping("/{serverName}")
    public ResponseEntity<ServerRequestDTO> getServerByName(@PathVariable(value = "serverName") String serverName) {
        return ResponseEntity.ok(serverService.getServerByName(serverName));
    }

    @DeleteMapping("/{serverName}")
    public void deleteServerByName(@PathVariable(value = "serverName") String serverName) {
        serverService.deleteServerByName(serverName);
    }
}
