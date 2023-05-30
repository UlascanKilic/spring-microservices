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
@Table(name = "server")
public class Server {

    @Id
    @GeneratedValue
    private Integer id;

    private String serverName;

    private Integer userCount;

    private String port;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "scene_id", referencedColumnName = "id")
    private Scene scene;

}
