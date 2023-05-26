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
@Table(name = "launcher_text")
public class LauncherText {

    @Id
    @GeneratedValue
    private Integer id;

    String connectionText;
    String startingText;
    String downloadUpdateText;
    String downloadGameText;
    String checkText;
    String downloadingText;
    String downloadingCompletedText;
    String extractText;
    String getVersionErrorText;
    String getGameErrorText;
    String gameVersionAndLinkError;
    String buttonContentUpdate;
    String buttonContentStart;
    String buttonContentDownload;

}
