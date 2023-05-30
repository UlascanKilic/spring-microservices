package com.ulascan.serverservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinSceneDTO {
    private String sceneName;
    private String password;
}
