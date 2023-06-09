package com.ulascan.serverservice.dto.scene;

import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.enums.UnityScene;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SceneRequestDTO {
    //TODO: add lessonCode and facultyName fields
    @NotNull
    private UnityScene unitySceneName;

    private int maxUserCapacity;

}
