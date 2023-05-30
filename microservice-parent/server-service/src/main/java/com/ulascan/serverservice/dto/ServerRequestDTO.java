package com.ulascan.serverservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerRequestDTO {

    private String serverName;

    private String unitySceneName;

    private Integer userCount;

}
