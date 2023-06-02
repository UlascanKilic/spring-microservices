package ulascan.userservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Error {

    USER_DOESNT_EXIST("userDoesntExist", "There isn't a user with those credentials."),
    EMAIL_IS_IN_USE("emailIsInUse", "Email is in use."),
    VERSION_NOT_FOUND("versionNotFound", "There isn't a valid version."),
    LAUNCHER_TEXT_NOT_FOUND("launcherTextNotFound", "There isn't a valid launcher text."),
    LAUNCHER_NOT_FOUND("launcherNotFound", "There isn't a valid launcher data."),
    RESET_PASSWORD_CODE_NOT_FOUND("resetPasswordCodeNotFound", "There isn't a valid password code."),
    RESET_PASSWORD_CODE_DOESNT_MATCH("resetPasswordCodeDoesntMatch", "Reset password code doesnt match."),
    VERIFICATION_DOESNT_EXIST("verificationCodeDoesntExist", "Verification code doesnt exist."),
    INACTIVE_USER("inactiveUser", "User is not active."),
    WRONG_PASSWORD("wrongPassword", "Passwords are doesnt match.");

    private final String errorCode;
    private final String errorMessage;
}
