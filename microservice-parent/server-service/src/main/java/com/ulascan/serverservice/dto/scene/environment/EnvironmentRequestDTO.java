package com.ulascan.serverservice.dto.scene.environment;

import com.ulascan.serverservice.dto.scene.SceneRequestDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class EnvironmentRequestDTO extends SceneRequestDTO {

    String description;

}
