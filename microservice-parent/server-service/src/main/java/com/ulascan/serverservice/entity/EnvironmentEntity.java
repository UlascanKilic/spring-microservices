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
@Table(name = "environment_scene")
public class EnvironmentEntity extends Scene {

    String description;
}
