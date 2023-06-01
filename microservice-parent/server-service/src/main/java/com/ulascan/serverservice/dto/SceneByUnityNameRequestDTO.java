package com.ulascan.serverservice.dto;

import com.ulascan.serverservice.enums.UnityScene;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SceneByUnityNameRequestDTO {
    @NotNull
    private UnityScene unityScene;
}
