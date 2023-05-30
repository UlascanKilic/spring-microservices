package com.ulascan.serverservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Error {

    USER_DOESNT_EXIST("userDoesntExist", "There isn't a user with those credentials."),
    VERSION_NOT_FOUND("versionNotFound", "There isn't a valid version."),
    LAUNCHER_TEXT_NOT_FOUND("launcherTextNotFound", "There isn't a valid launcher text."),
    LAUNCHER_NOT_FOUND("launcherNotFound", "There isn't a valid launcher data."),
    SERVER_DOESNT_EXIST("serverDoesntExist", "There isn't a server with those credentials."),
    NO_FREE_SERVER_FOUND("noFreeServerFound", "There isn't a free server right now."),
    SCENE_NOT_FOUND("sceneNotFound", "There isn't a scene with those credentials"),
    SCENE_PASSWORD_DOESNT_MATCH("scenePasswordDoesntMatch", "Scene password doesnt match"),
    DUPLICATE_SCENE_NAME("duplicateSceneName", "DUPLICATE_SCENE_NAME"),
    HOST_ALREADY_EXISTS("hostAlreadyExists", "HOST_ALREADY_EXISTS");

    //TODO isimlendirmeleri düzelt

    private final String errorCode;
    private final String errorMessage;
}
