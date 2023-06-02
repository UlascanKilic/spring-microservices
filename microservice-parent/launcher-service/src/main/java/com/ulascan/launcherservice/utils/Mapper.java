package com.ulascan.launcherservice.utils;

import com.ulascan.launcherservice.dto.LauncherDTO;
import com.ulascan.launcherservice.dto.LauncherTextDTO;
import com.ulascan.launcherservice.dto.VersionDTO;
import com.ulascan.launcherservice.entity.Launcher;
import com.ulascan.launcherservice.entity.LauncherText;
import com.ulascan.launcherservice.entity.Version;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

    public VersionDTO entityToDTO(Version version) {
        return modelMapper.map(version, VersionDTO.class);
    }

    public Launcher dtoToEntity(LauncherDTO dto, Launcher launcher) {
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
        this.modelMapper.map(dto, launcher);
        return launcher;
    }

    public LauncherDTO entityToDTO(Launcher launcher) {
        return modelMapper.map(launcher, LauncherDTO.class);
    }

    public LauncherText dtoToEntity(LauncherTextDTO dto, LauncherText launcherText) {
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
        this.modelMapper.map(dto, launcherText);
        return launcherText;
    }

    public LauncherTextDTO entityToDTO(LauncherText launcherTextcher) {
        return modelMapper.map(launcherTextcher, LauncherTextDTO.class);
    }
}
