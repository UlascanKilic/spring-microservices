package com.ulascan.launcherservice.service;

import com.ulascan.launcherservice.dto.LauncherDTO;
import com.ulascan.launcherservice.dto.LauncherTextDTO;

public interface ILauncherService {

    void addLauncher(LauncherDTO dto);
    LauncherDTO getLauncher();
    void setLauncherText(LauncherTextDTO dto);
    LauncherTextDTO getLauncherText();

}
