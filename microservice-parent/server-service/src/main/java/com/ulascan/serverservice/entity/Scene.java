package com.ulascan.serverservice.entity;

import com.ulascan.serverservice.enums.SceneType;
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

    private String unitySceneName; //enum yap
    private String sceneName;
    private String scenePassword;
    private String hostEmail;
    private String hostFirstName;
    private String hostLastName;

    @Value("${entity.max.user.count}")
    private int maxUserCapacity;

    private SceneType sceneType;
    private boolean isPrivateScene;
    private boolean active;

    @OneToOne(mappedBy = "scene")
    private Server server;

}
