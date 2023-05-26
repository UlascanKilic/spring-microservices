package com.ulascan.serverservice.service;

import com.ulascan.serverservice.dto.StatusCountDTO;
import com.ulascan.serverservice.dto.StatusDTO;
import com.ulascan.serverservice.entity.Status;
import com.ulascan.serverservice.exception.BadRequestException;
import com.ulascan.serverservice.exception.Error;
import com.ulascan.serverservice.repository.StatusRepository;
import com.ulascan.serverservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    private final Mapper mapper;

    @Value("${entity.max.user.count}")
    private Integer maxUserCount;

    @Value("${entity.default.isup}")
    private boolean defaultIsUp;

    public List<StatusDTO> getAllServerStatus() {

        return mapper.mapList(statusRepository.findAll(), StatusDTO.class);
    }

    @Transactional
    public void setServerStatus(StatusDTO statusDTO) {
        Status status = statusRepository.findByServerName(statusDTO.getServerName());

        /*status.setUp(statusDTO.isUp());
            status.setUserCount(statusDTO.getUserCount());
            status.setServerName(statusDTO.getServerName());
            status.setFull(statusDTO.isFull());
            status.setMaxUserCapacity(statusDTO.getMaxUserCapacity());*/
        status = mapper.dtoToEntity(statusDTO, Objects.requireNonNullElseGet(status, Status::new));

        statusRepository.save(status);
    }

    @Transactional
    public void setServerCount(StatusCountDTO statusCountDTO) {
        Status status = statusRepository.findByServerName(statusCountDTO.getServerName());


        if(status == null)
        {
            status = Status.builder()
                    .maxUserCapacity(maxUserCount)
                    .serverName(statusCountDTO.getServerName())
                    .userCount(statusCountDTO.getUserCount())
                    .isUp(defaultIsUp)
                    .build();
        }
        else {
            status = mapper.dtoToEntity(statusCountDTO, status);
        }

        statusRepository.save(status);
    }


    public StatusDTO getServerStatusByName(String serverName) {
        Status status = statusRepository.findByServerName(serverName);

        if(status == null) throw new BadRequestException(Error.SERVER_DOESNT_EXIST.getErrorCode(), Error.SERVER_DOESNT_EXIST.getErrorMessage());

        return mapper.entityToDTO(status) ;
    }

    @Transactional
    public void deleteStatusByName(String serverName) {
        Status status = statusRepository.findByServerName(serverName);

        if(status == null) throw new BadRequestException(Error.SERVER_DOESNT_EXIST.getErrorCode(), Error.SERVER_DOESNT_EXIST.getErrorMessage());

        statusRepository.delete(status);
    }
}
