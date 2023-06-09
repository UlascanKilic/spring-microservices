package com.ulascan.serverservice.dto.scene.session;


import com.ulascan.serverservice.dto.scene.SceneRequestDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionRequestDTO extends SceneRequestDTO {

     String lecturerFirstName;
     String lecturerLastName;
     String lecturerEmail;
     String lessonCode;
     String sessionName; //unique

     boolean isPrivateScene;
}
