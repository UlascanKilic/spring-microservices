package com.ulascan.serverservice.controller;

import com.ulascan.serverservice.dto.StatusCountDTO;
import com.ulascan.serverservice.dto.StatusDTO;
import com.ulascan.serverservice.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/server/status")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping
    public ResponseEntity<List<StatusDTO>> getAllServerStatus(){
        return ResponseEntity.ok(statusService.getAllServerStatus());
    }

    @PostMapping
    public void setServerStatus(@RequestBody StatusDTO statusDTO)
    {
        statusService.setServerStatus(statusDTO);
    }

    @PostMapping("/count")
    public void setServerUserCount(@RequestBody StatusCountDTO statusCountDTO)
    {
        statusService.setServerCount(statusCountDTO);
    }

    @GetMapping("/{serverName}")
    public ResponseEntity<StatusDTO> getServerStatusByName(@PathVariable(value = "serverName") String serverName) {
        return ResponseEntity.ok(statusService.getServerStatusByName(serverName));
    }

    @DeleteMapping("/{serverName}")
    public void deleteServerStatusByName(@PathVariable(value = "serverName") String serverName) {
        statusService.deleteStatusByName(serverName);
    }
}
