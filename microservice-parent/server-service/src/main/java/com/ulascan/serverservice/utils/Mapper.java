package com.ulascan.serverservice.utils;

import com.ulascan.serverservice.dto.StatusCountDTO;
import com.ulascan.serverservice.dto.StatusDTO;
import com.ulascan.serverservice.entity.Status;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class Mapper {
    @Autowired
    private ModelMapper modelMapper;

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }


    public Status dtoToEntity(StatusDTO statusDTO, Status status) {
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);

        this.modelMapper.map(statusDTO, status);

        status.setFull(statusDTO.getUserCount() >= status.getMaxUserCapacity());
        return status;
    }

    public Status dtoToEntity(StatusCountDTO statusCountDTO, Status status) {
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);

        this.modelMapper.map(statusCountDTO, status);

        status.setFull(statusCountDTO.getUserCount() >= status.getMaxUserCapacity());
        return status;
    }

    public StatusDTO entityToDTO(Status status) {
        return modelMapper.map(status, StatusDTO.class);
    }
}
