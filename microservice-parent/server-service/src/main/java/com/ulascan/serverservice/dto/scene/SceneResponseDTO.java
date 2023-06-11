package com.ulascan.serverservice.dto.scene;

import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.enums.UnityScene;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class SceneResponseDTO implements Serializable {

    private String name; //unique

    private UnityScene unityScene;

    private String port;

    private SceneType sceneType;

    private int userCount;
    private int maxUserCapacity;

}
