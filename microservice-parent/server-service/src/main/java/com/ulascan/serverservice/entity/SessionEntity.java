package com.ulascan.serverservice.entity;

import com.ulascan.serverservice.enums.UnityScene;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "session_scene")
public class SessionEntity extends Scene{

    String lecturerFirstName;
    String lecturerLastName;
    String lecturerEmail;
    String scenePassword;
    String lessonCode;


    boolean privateScene;
}
