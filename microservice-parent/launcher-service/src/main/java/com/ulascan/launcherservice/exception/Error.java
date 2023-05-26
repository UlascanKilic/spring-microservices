package com.ulascan.launcherservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Error {

    USER_DOESNT_EXIST("userDoesntExist", "There isn't a user with those credentials."),
    VERSION_NOT_FOUND("versionNotFound", "There isn't a valid version."),
    LAUNCHER_TEXT_NOT_FOUND("launcherTextNotFound", "There isn't a valid launcher text."),
    LAUNCHER_NOT_FOUND("launcherNotFound", "There isn't a valid launcher data.");

    private final String errorCode;
    private final String errorMessage;
}
