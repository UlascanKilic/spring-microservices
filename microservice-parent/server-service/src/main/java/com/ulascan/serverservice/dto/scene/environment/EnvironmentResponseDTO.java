package com.ulascan.serverservice.dto.scene.environment;

import com.ulascan.serverservice.dto.scene.SceneResponseDTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentResponseDTO extends SceneResponseDTO {
    private String environmentName;
    private String description ;
}
