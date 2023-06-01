package com.ulascan.launcherservice.service;

import com.ulascan.launcherservice.dto.VersionDTO;
import com.ulascan.launcherservice.entity.Version;
import com.ulascan.launcherservice.exception.BadRequestException;
import com.ulascan.launcherservice.exception.Error;
import com.ulascan.launcherservice.repository.VersionRepository;
import com.ulascan.launcherservice.utils.Mapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Service class for managing versions in the launcher service.
 *
 * @author S. Ulascan Kilic
 */
@Service
@RequiredArgsConstructor
public class VersionService implements IVersionService{

    private final VersionRepository versionRepository;

    private final Mapper mapper;

    /**
     * Retrieves the current version from the repository.
     *
     * @return The current version as a VersionDTO object.
     * @throws BadRequestException if no version is found.
     */
    public VersionDTO getVersion() {
        Version version = versionRepository.findFirstByOrderByVersion();

        if(version == null) throw new BadRequestException(Error.VERSION_NOT_FOUND.getErrorCode(), Error.VERSION_NOT_FOUND.getErrorMessage());

        return  mapper.entityToDTO(version);
    }

    /**
     * Sets a new version in the repository.
     * If no version exists, a new Version object is created with the provided version data.
     * If a version exists, it is updated with the provided version data.
     *
     * @param versionDTO The VersionDTO object containing the new version data.
     */
    @Transactional
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
