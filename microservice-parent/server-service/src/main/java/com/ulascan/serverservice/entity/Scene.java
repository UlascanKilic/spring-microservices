package com.ulascan.serverservice.entity;

import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.enums.UnityScene;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "scene")
public class Scene {
    @Id
    @GeneratedValue
    private Integer id;

    private UnityScene unityScene; //enum yap
    private String sceneName;
    private String scenePassword;
    private String hostEmail;
    private String hostFirstName;
    private String hostLastName;

    private int maxUserCapacity;

    @Enumerated(EnumType.ORDINAL)
    private SceneType sceneType;

    private boolean privateScene;
    private boolean active;

    @OneToOne(mappedBy = "scene")
    private Server server;

}
