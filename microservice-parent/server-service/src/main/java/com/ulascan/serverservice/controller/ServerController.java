package com.ulascan.serverservice.controller;

import com.ulascan.serverservice.dto.ServerCountDTO;
import com.ulascan.serverservice.dto.ServerDTO;
import com.ulascan.serverservice.dto.ServerResponseDTO;
import com.ulascan.serverservice.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/server/status")
@RequiredArgsConstructor
public class ServerController {

    private final ServerService serverService;

    @GetMapping
    public ResponseEntity<List<ServerDTO>> getAllServerStatus(){
        return ResponseEntity.ok(serverService.getAllServers());
    }

    @PostMapping
    public ResponseEntity<ServerResponseDTO> setServer(@RequestBody ServerDTO serverDTO)
    {
         return ResponseEntity.ok(serverService.setServer(serverDTO));
    }

    /*@PostMapping("/count")
    public ResponseEntity<ServerResponseDTO> setServerUserCount(@RequestBody ServerCountDTO serverCountDTO)
    {
        return ResponseEntity.ok(serverService.setServerCount(serverCountDTO));
    }*/

    @GetMapping("/{serverName}")
    public ResponseEntity<ServerDTO> getServerByName(@PathVariable(value = "serverName") String serverName) {
        return ResponseEntity.ok(serverService.getServerByName(serverName));
    }

    @DeleteMapping("/{serverName}")
    public void deleteServerByName(@PathVariable(value = "serverName") String serverName) {
        serverService.deleteServerByName(serverName);
    }

    @DeleteMapping("/close/{serverName}")
    public ResponseEntity<Void> deleteSceneByServerName(@PathVariable(value = "serverName") String serverName) {
        serverService.deleteSceneByServerName(serverName);

        return ResponseEntity.ok().build();
    }
}
