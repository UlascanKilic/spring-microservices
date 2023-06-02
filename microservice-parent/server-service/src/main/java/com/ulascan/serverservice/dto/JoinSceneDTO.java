package com.ulascan.serverservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinSceneDTO {

    @NotNull
    private String sceneName;

    @NotNull
    private String password;
}
