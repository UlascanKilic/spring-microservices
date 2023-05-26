package com.ulascan.serverservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO {

    private String serverName;

    private Integer userCount;

    private boolean isUp;

    private boolean isFull;

    private int maxUserCapacity;

}
