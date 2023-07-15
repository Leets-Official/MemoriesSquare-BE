package leets.memoriessquare.domain.photo.exception;

import leets.memoriessquare.global.error.ErrorCode;
import leets.memoriessquare.global.error.exception.ServiceException;

public class MimeTypeIsNotImageException extends ServiceException {
    public MimeTypeIsNotImageException() {
        super(ErrorCode.MIME_TYPE_IS_NOT_IMAGE);
    }
}
