package com.ulascan.notificationservice.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMailEvent {
    private String firstName;
    private String lastName;
    private String email;
    private String verificationCode;
}