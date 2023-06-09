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

    String eventName; //unique
    String organizerName;
    String date;
    String scenePassword; //TODO batuyla konus gerek var mi
    String description;

    byte[] eventImage;

    boolean isLive; //false default. 
    boolean isPrivate;
}
