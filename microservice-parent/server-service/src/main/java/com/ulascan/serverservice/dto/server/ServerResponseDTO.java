package com.ulascan.serverservice.dto.server;

import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.enums.UnityScene;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerResponseDTO {
    private UnityScene unityScene;
    private SceneType sceneType;

}
