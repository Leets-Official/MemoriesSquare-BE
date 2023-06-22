package leets.memoriessquare.global.jwt.exception;

import leets.memoriessquare.global.error.ErrorCode;
import leets.memoriessquare.global.error.exception.ServiceException;

public class InvalidTokenException extends ServiceException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
