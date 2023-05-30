package com.ulascan.serverservice.dto;

import com.ulascan.serverservice.enums.SceneType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SceneByTypeRequestDTO {
    private SceneType sceneType;
}
