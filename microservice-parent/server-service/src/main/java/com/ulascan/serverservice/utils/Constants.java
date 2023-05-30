package com.ulascan.serverservice.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    //todo
    @Value("${entity.default.password}")
    public static String DEFAULT_PASSWORD = "";

    @Value("${entity.default.email}")
    public static String DEFAULT_EMAIL;

    @Value("${entity.max.user.count}")
    public static int MAX_USER_COUNT = 60;
}
