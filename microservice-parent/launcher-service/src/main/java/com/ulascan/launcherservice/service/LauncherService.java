package com.ulascan.launcherservice.service;

import com.ulascan.launcherservice.dto.LauncherDTO;
import com.ulascan.launcherservice.dto.LauncherTextDTO;
import com.ulascan.launcherservice.entity.Launcher;
import com.ulascan.launcherservice.entity.LauncherText;
import com.ulascan.launcherservice.exception.BadRequestException;
import com.ulascan.launcherservice.exception.Error;
import com.ulascan.launcherservice.repository.LauncherRepository;
import com.ulascan.launcherservice.repository.LauncherTextRepository;
import com.ulascan.launcherservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing launchers and launcher text.
 * Implements the ILauncherService interface.
 *
 * @author S. Ulascan Kilic
 */
@Service
@RequiredArgsConstructor
public class LauncherService implements ILauncherService{

    private final LauncherRepository launcherRepository;

    private final LauncherTextRepository launcherTextRepository;

    private final Mapper mapper;

    /**
     * Adds a new launcher to the repository or updates an existing one.
     * If no launcher exists, a new Launcher object is created with the provided launcher data.
     * If a launcher exists, it is updated with the provided launcher data.
     *
     * @param dto The LauncherDTO object containing the launcher data.
     */
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

    /**
     * Retrieves the launcher from the repository.
     *
     * @return The LauncherDTO object containing the launcher data.
     * @throws BadRequestException if no launcher is found.
     */
    public LauncherDTO getLauncher() {
        Launcher launcher = launcherRepository.findFirstByOrderById();

        if(launcher == null) throw new BadRequestException(Error.LAUNCHER_NOT_FOUND.getErrorCode(), Error.LAUNCHER_NOT_FOUND.getErrorMessage());

        return mapper.entityToDTO(launcher);

    }

    /**
     * Sets the launcher text in the repository.
     * If no launcher text exists, a new LauncherText object is created with the provided text data.
     * If launcher text exists, it is updated with the provided text data.
     *
     * @param dto The LauncherTextDTO object containing the text data.
     */
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
    /**
     * Retrieves the launcher text from the repository.
     *
     * @return The LauncherTextDTO object containing the launcher text data.
     * @throws BadRequestException if no launcher text is found.
     */
    public LauncherTextDTO getLauncherText() {

        LauncherText launcherText = launcherTextRepository.findFirstByOrderById();

        if(launcherText == null) throw new BadRequestException(Error.LAUNCHER_TEXT_NOT_FOUND.getErrorCode(), Error.LAUNCHER_TEXT_NOT_FOUND.getErrorMessage());

        return mapper.entityToDTO(launcherText);

    }
}
