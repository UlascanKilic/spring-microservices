package com.ulascan.launcherservice.controller;

import com.ulascan.launcherservice.dto.LauncherDTO;
import com.ulascan.launcherservice.dto.LauncherGameDTO;
import com.ulascan.launcherservice.dto.LauncherTextDTO;
import com.ulascan.launcherservice.service.LauncherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/launcher")
@RequiredArgsConstructor
public class LauncherController {

    @Autowired
    private LauncherService launcherService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private void addLauncher(@RequestBody LauncherDTO dto) {
        launcherService.addLauncher(dto);
    }

    @GetMapping
    private ResponseEntity<LauncherDTO> getLauncher()
    {
         return ResponseEntity.ok(launcherService.getLauncher());
    }

    @PostMapping("/text")
    @ResponseStatus(HttpStatus.CREATED)
    private void setLauncherText(@RequestBody LauncherTextDTO dto) {
        launcherService.setLauncherText(dto);
    }

    @GetMapping("/text")
    private ResponseEntity<LauncherTextDTO> getLauncherText() {

        return ResponseEntity.ok(launcherService.getLauncherText());
    }
}
