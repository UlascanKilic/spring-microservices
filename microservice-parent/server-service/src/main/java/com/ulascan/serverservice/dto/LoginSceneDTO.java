package com.ulascan.serverservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginSceneDTO {
    private String sceneName;
    private String password;
}
