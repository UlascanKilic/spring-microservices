package com.ulascan.serverservice.dto;

import com.ulascan.serverservice.enums.UnityScene;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerResponseDTO {
    private UnityScene unityScene;

}
