package leets.memoriessquare.domain.photo.exception;

import leets.memoriessquare.global.error.ErrorCode;
import leets.memoriessquare.global.error.exception.ServiceException;

public class PhotoNotFoundException extends ServiceException {
    public PhotoNotFoundException() {
        super(ErrorCode.PHOTO_NOT_FOUND);
    }
}
