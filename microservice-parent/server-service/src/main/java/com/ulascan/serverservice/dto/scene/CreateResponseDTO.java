package com.ulascan.serverservice.dto.scene;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateResponseDTO {
    private String password;
}
