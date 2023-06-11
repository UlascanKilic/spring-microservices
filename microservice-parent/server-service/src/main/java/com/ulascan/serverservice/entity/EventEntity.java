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
@Table(name = "event_scene")
public class EventEntity extends Scene {

    String organizerName;
    String date;
    String scenePassword;// scenePassword is unique
    String description;

    byte[] eventImage;

    boolean live; //false default.
    boolean privateScene;

}
