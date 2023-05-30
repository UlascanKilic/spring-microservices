package com.ulascan.serverservice.dto;

import com.ulascan.serverservice.enums.SceneType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SceneResponseDTO {

    private String unitySceneName;
    private String sceneName;
    private String hostFirstName;
    private String hostLastName;
    private String hostEmail;
    private String port;

    private SceneType sceneType;

    private boolean isPrivateScene;

    private int userCount;
    private int maxUserCapacity;

}
