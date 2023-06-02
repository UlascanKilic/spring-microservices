package com.ulascan.launcherservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LauncherTextDTO {
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
