package com.ulascan.serverservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "server_status")
public class Status {

    @Id
    @GeneratedValue
    private Integer id;

    private String serverName;

    private Integer userCount;

    @Value("${entity.default.isup}")
    private boolean isUp = true;

    private boolean isFull = false;

    @Value("${entity.max.user.count}")
    private int maxUserCapacity;
}
