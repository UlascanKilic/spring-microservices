package com.ulascan.serverservice.dto;

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
    //todo host email
    private String port;

    private boolean isDefaultScene;
    private boolean isPrivateScene;

    private int userCount;
    private int maxUserCapacity;

}
