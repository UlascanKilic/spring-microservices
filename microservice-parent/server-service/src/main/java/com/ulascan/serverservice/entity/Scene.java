package com.ulascan.serverservice.entity;

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

    private String unitySceneName;
    private String sceneName;
    private String scenePassword;
    private String hostEmail;
    private String hostFirstName;
    private String hostLastName;

    @Value("${entity.max.user.count}")
    private int maxUserCapacity;

    //todo type a gore al boolean olmasin
    private boolean isDefaultScene; //default scene = Ortabahce
    private boolean isPrivateScene;
    private boolean active;

    @OneToOne(mappedBy = "scene")
    private Server server;

}
