package com.ulascan.serverservice.dto;

import com.ulascan.serverservice.enums.SceneType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SceneByTypeRequestDTO {
    @NotNull
    private SceneType sceneType;
}
