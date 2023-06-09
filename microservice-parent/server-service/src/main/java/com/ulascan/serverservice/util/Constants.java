package com.ulascan.serverservice.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Constants {
    @Getter
    private static String defaultPassword;

    @Getter
    private static String defaultEmail;

    @Getter
    private static String defaultFirstName;

    @Getter
    private static String defaultLastName;

    @Getter
    private static String defaultSceneName;

    @Getter
    private static String defaultDescription;

    @Getter
    private static int maxUserCount;

    @Value("${entity.default.description}")
    public void setDefaultDescription(String description) {
        Constants.defaultDescription = description;
    }

    @Value("${entity.default.password}")
    public void setDefaultPassword(String password){Constants.defaultPassword = password; }

    @Value("${entity.default.email}")
    public void setDefaultEmail(String email){Constants.defaultEmail = email;}

    @Value("${entity.max.user.count}")
    public void setMaxUserCount(int maxUserCount){Constants.maxUserCount = maxUserCount;}

    @Value("${entity.default.firstname}")
    public void setDefaultFirstName(String firstName){Constants.defaultFirstName = firstName;}

    @Value("${entity.default.lastname}")
    public void setDefaultLastName(String defaultLastName){Constants.defaultLastName = defaultLastName;}

    @Value("${entity.default.sceneName}")
    public void setDefaultSceneName(String defaultSceneName){Constants.defaultSceneName = defaultSceneName;}



}
