package com.ulascan.launcherservice.controller;

import com.ulascan.launcherservice.dto.VersionDTO;
import com.ulascan.launcherservice.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/launcher/version")
@RequiredArgsConstructor
public class VersionController {

    private final VersionService versionService;

    @GetMapping
    private ResponseEntity<VersionDTO>  getVersion() {
        return ResponseEntity.ok(versionService.getVersion());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private void setVersion(@RequestBody VersionDTO versionDTO)
    {
        versionService.setVersion(versionDTO);
    }
}
