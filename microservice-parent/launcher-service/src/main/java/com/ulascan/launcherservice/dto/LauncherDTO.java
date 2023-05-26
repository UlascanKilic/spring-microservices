package com.ulascan.launcherservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LauncherDTO {
    private String gameLink;
    private String gameName;
    private String gameZipName;
    private String gameFolder;
    private String localGameVersionName;
    private String localGameLocationFolder;
}
