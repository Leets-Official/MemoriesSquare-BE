package leets.memoriessquare.domain.photo.usecase;

import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CropImageImpl implements CropImage {
    private static final int IMAGE_HEIGHT = 500;

    @Override
    public void execute(File originalImage, String resultFilePath) throws IOException {
        BufferedImage readImage = ImageIO.read(originalImage);

        int width = readImage.getWidth();
        int height = readImage.getHeight();
        int min = Math.min(width, height);

        BufferedImage croppedImage = Scalr.crop(readImage, (width - min) / 2, (height - min) / 2, min, min);
        BufferedImage result = Scalr.resize(croppedImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, IMAGE_HEIGHT);

        String originalFileName = originalImage.getName();
        String extension = StringUtils.getFilenameExtension(originalFileName);

        // TODO: 이미지 업로드 시스템과 연결 필요. 현재는 단순히 파일을 받아서 크롭 후 다른 이름으로 저장합니다.
        ImageIO.write(result, Objects.requireNonNullElse(extension, "png"), new File(resultFilePath));
    }
}
