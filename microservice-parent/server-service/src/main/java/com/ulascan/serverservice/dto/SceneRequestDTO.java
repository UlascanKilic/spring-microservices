package com.ulascan.serverservice.dto;

import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.enums.UnityScene;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SceneRequestDTO {
    //TODO: add lessonCode and facultyName fields
    @NotNull
    private UnityScene unityScene;

    @NotNull
    private String sceneName;

    @NotNull
    private String hostFirstName;

    @NotNull
    private String hostLastName;

    @NotNull
    private String hostEmail;

    @NotNull
    private SceneType sceneType;

    @NotNull
    private int maxUserCapacity;

    @NotNull
    private boolean privateScene;
}
