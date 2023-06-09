package com.ulascan.serverservice.dto.scene.session;

import com.ulascan.serverservice.dto.scene.SceneResponseDTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponseDTO extends SceneResponseDTO {

    private String lecturerFirstName;
    private String lecturerLastName;
    private String lecturerEmail;
    private String lessonCode;
    private String sessionName; //unique

    private boolean isPrivate;
}
