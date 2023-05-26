package com.ulascan.launcherservice.service;

import com.ulascan.launcherservice.dto.VersionDTO;
import com.ulascan.launcherservice.entity.Version;
import com.ulascan.launcherservice.repository.VersionRepository;
import com.ulascan.launcherservice.utils.Mapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VersionService {

    private final VersionRepository versionRepository;

    private final Mapper mapper;
    public VersionDTO getVersion() {
        Version version = versionRepository.findFirstByOrderByVersion();

        return version == null ? VersionDTO.builder().build() :  mapper.entityToDTO(version);
    }

    public void setVersion(VersionDTO versionDTO) {
        Version version = versionRepository.findFirstByOrderByVersion();
        if(version == null){
            version = Version.builder()
                    .version(versionDTO.getVersion())
                    .versionDate(LocalDate.now())
                    .build();
        }
        else {
            version.setVersion(versionDTO.getVersion());
            version.setVersionDate(LocalDate.now());
        }

        versionRepository.save(version);
    }
}
