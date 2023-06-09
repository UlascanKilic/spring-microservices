package com.ulascan.serverservice.dto.scene.event;

import com.ulascan.serverservice.dto.scene.SceneResponseDTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDTO extends SceneResponseDTO {

    private String eventName;
    private String organizerName;
    private String date;
    private String description;

    private boolean isLive;
    private boolean isPrivateScene;

    private byte[] eventImage;
}
