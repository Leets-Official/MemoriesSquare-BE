package leets.memoriessquare.domain.image.usecase;

import java.io.File;
import java.io.IOException;

public interface CropImage {
    void execute(File originalImage, String resultFilePath) throws IOException;
}
