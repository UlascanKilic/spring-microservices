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

    private String name;

    @NotNull
    private UnityScene unityScene;

    private int maxUserCapacity;

}
