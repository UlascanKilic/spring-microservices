package com.ulascan.serverservice.dto;

import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.enums.UnityScene;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SceneResponseDTO {

    private UnityScene unityScene;
    private String sceneName;
    private String hostFirstName;
    private String hostLastName;
    private String hostEmail;
    private String port;

    private SceneType sceneType;

    private boolean privateScene;

    private int userCount;
    private int maxUserCapacity;

}
