package com.ulascan.launcherservice.service;

import com.ulascan.launcherservice.dto.LauncherDTO;
import com.ulascan.launcherservice.dto.LauncherGameDTO;
import com.ulascan.launcherservice.dto.LauncherTextDTO;
import com.ulascan.launcherservice.entity.Launcher;
import com.ulascan.launcherservice.entity.LauncherText;
import com.ulascan.launcherservice.exception.BadRequestException;
import com.ulascan.launcherservice.exception.Error;
import com.ulascan.launcherservice.repository.LauncherRepository;
import com.ulascan.launcherservice.repository.LauncherTextRepository;
import com.ulascan.launcherservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LauncherService {

    private final LauncherRepository launcherRepository;

    private final LauncherTextRepository launcherTextRepository;

    private final Mapper mapper;

    @Transactional
    public void addLauncher(LauncherDTO dto) {

        Launcher launcher = launcherRepository.findFirstByOrderById();

        if(launcher == null)
        {
            launcher = Launcher.builder()
                    .gameLink(dto.getGameLink())
                    .gameName(dto.getGameName())
                    .gameZipName(dto.getGameZipName())
                    .gameFolder(dto.getGameFolder())
                    .localGameVersionName(dto.getLocalGameVersionName())
                    .localGameLocationFolder(dto.getLocalGameLocationFolder())
                    .build();
        }
        else {
            launcher = mapper.dtoToEntity(dto, launcher);
        }

        launcherRepository.save(launcher);
    }

    public LauncherDTO getLauncher() {
        Launcher launcher = launcherRepository.findFirstByOrderById();

        if(launcher == null) throw new BadRequestException(Error.LAUNCHER_NOT_FOUND.getErrorCode(), Error.LAUNCHER_NOT_FOUND.getErrorMessage());

        return mapper.entityToDTO(launcher);

    }
    @Transactional
    public void setLauncherText(LauncherTextDTO dto) {

        LauncherText launcherText = launcherTextRepository.findFirstByOrderById();
        
        if(launcherText == null)
        {
            launcherText = LauncherText.builder()
                    .checkText(dto.getCheckText())
                    .connectionText(dto.getConnectionText())
                    .buttonContentDownload(dto.getButtonContentDownload())
                    .downloadGameText(dto.getDownloadGameText())
                    .downloadingText(dto.getDownloadingText())
                    .extractText(dto.getExtractText())
                    .downloadingCompletedText(dto.getDownloadingCompletedText())
                    .downloadUpdateText(dto.getDownloadUpdateText())
                    .startingText(dto.getStartingText())
                    .buttonContentStart(dto.getButtonContentStart())
                    .getGameErrorText(dto.getGetGameErrorText())
                    .buttonContentUpdate(dto.getButtonContentUpdate())
                    .gameVersionAndLinkError(dto.getGameVersionAndLinkError())
                    .getVersionErrorText(dto.getGetVersionErrorText())
                    .build();
        }
        else{
            launcherText = mapper.dtoToEntity(dto, launcherText);
        }
        
        launcherTextRepository.save(launcherText);
    }

    public LauncherTextDTO getLauncherText() {

        LauncherText launcherText = launcherTextRepository.findFirstByOrderById();

        if(launcherText == null) throw new BadRequestException(Error.LAUNCHER_TEXT_NOT_FOUND.getErrorCode(), Error.LAUNCHER_TEXT_NOT_FOUND.getErrorMessage());

        return mapper.entityToDTO(launcherText);

    }
}
