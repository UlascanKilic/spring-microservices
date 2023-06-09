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
@Table(name = "scene")
@Inheritance(strategy = InheritanceType.JOINED)
public class Scene {
    @Id
    @GeneratedValue
    private Integer id;

    public UnityScene unitySceneName;

    private int maxUserCapacity;

    @Enumerated(EnumType.ORDINAL)
    private SceneType sceneType;

    private boolean active;

    @OneToOne(mappedBy = "scene")
    private Server server;

}
