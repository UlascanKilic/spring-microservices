package com.ulascan.serverservice.dto.scene.session;

import com.ulascan.serverservice.dto.scene.CreateResponseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateSessionResponseDTO extends CreateResponseDTO {
}
