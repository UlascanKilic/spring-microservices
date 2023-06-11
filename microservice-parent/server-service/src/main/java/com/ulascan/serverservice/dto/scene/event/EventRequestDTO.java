package com.ulascan.serverservice.dto.scene.event;

import com.ulascan.serverservice.dto.scene.SceneRequestDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventRequestDTO extends SceneRequestDTO {

    String organizerName;
    String date;
    String description;

    byte[] eventImage;

    boolean isLive; //false default. 
    boolean isPrivateScene;
}
