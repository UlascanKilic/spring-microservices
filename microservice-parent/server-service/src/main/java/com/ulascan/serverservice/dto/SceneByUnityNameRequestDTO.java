package com.ulascan.serverservice.dto;

import com.ulascan.serverservice.enums.UnityScene;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SceneByUnityNameRequestDTO {
    private UnityScene unityScene;
}
