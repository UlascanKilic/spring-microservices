package com.ulascan.serverservice.dto.scene.event;

import com.ulascan.serverservice.dto.scene.PasswordDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventPasswordDTO extends PasswordDTO {
}
