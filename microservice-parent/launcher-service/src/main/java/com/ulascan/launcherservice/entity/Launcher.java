package com.ulascan.launcherservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "launcher")
public class Launcher {
    @Id
    @GeneratedValue
    private Integer id;

    private String gameLink;
    private String gameName;
    private String gameZipName;
    private String gameFolder;
    private String localGameVersionName;
    private String localGameLocationFolder;

}
