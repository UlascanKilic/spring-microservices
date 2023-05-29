package com.ulascan.serverservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerDTO {

    private String serverName;

    private String unitySceneName;

    private Integer userCount;

    private boolean isUp;

    private boolean isFull;




}
