package com.ulascan.serverservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerCountDTO {
    private String serverName;
    private String activeScene;

    private Integer userCount;


}
