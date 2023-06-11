package ulascan.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends BaseException {

    public ConflictException(String errorCode, String errorMessage, Object... parameters) {
        super(errorCode, String.format(errorMessage, parameters));
    }

}
