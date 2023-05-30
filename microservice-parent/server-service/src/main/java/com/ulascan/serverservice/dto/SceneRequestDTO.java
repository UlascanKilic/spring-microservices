package com.ulascan.serverservice.dto;

import com.ulascan.serverservice.enums.SceneType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SceneRequestDTO {

    private String unitySceneName;
    private String sceneName;
    private String hostFirstName;
    private String hostLastName;
    private String hostEmail;
    private String scenePassword;
    private SceneType sceneType;
    private int maxUserCapacity;

    private boolean isPrivateScene;
}
