package com.ulascan.launcherservice.service;

import com.ulascan.launcherservice.dto.VersionDTO;

public interface IVersionService {
    VersionDTO getVersion();
    void setVersion(VersionDTO versionDTO);
}
