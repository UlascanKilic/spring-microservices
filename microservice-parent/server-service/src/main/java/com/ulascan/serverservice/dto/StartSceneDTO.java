package com.ulascan.serverservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StartSceneDTO {

    private String unitySceneName;
    private String sceneName;
    private String hostFirstName;
    private String hostLastName;
    private String hostEmail;
    private String scenePassword;

    private int maxUserCapacity;

    private boolean isPrivateScene;
}
