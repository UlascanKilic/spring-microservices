package com.ulascan.serverservice.dto.server;

import com.ulascan.serverservice.enums.UnityScene;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerRequestDTO {

    @NotNull
    private String serverName;

    @NotNull
    private UnityScene unityScene;

    @NotNull
    private Integer userCount;

}
