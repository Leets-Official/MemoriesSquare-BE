package leets.memoriessquare.domain.photo.usecase;

import leets.memoriessquare.domain.photo.domain.Photo;
import leets.memoriessquare.domain.photo.exception.PhotoNotFoundException;
import leets.memoriessquare.domain.photo.presentation.dto.PhotoDTO;
import leets.memoriessquare.domain.photo.repository.PhotoRepository;
import leets.memoriessquare.domain.user.domain.User;
import leets.memoriessquare.domain.user.exception.UserNotFoundException;
import leets.memoriessquare.domain.user.repository.UserRepository;
import leets.memoriessquare.global.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CropPhotoImpl implements CropPhoto {
    private final S3Service s3Service;
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private static final int IMAGE_HEIGHT = 500;

    @Override
    public PhotoDTO execute(MultipartFile originalImage, UUID userId, UUID originalPhotoId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        BufferedImage readImage = ImageIO.read(originalImage.getInputStream());
        String extension = StringUtils.getFilenameExtension(originalImage.getOriginalFilename());

        int width = readImage.getWidth();
        int height = readImage.getHeight();
        int min = Math.min(width, height);

        BufferedImage croppedImage = Scalr.crop(readImage, (width - min) / 2, (height - min) / 2, min, min);
        BufferedImage result = Scalr.resize(croppedImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, IMAGE_HEIGHT);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(result, Objects.requireNonNullElse(extension, "png"), os);

            String savedFileName = UUID.randomUUID() + "-cropped";
            String url = s3Service.uploadImage(os, savedFileName, originalImage.getContentType(), extension);

            Photo originalPhoto = photoRepository.findById(originalPhotoId).orElseThrow(PhotoNotFoundException::new);
            Photo photo = Photo.builder()
                    .user(user)
                    .imageUrl(url)
                    .originalPhoto(originalPhoto)
                    .build();
            photoRepository.save(photo);

            return new PhotoDTO(photo.getId(), user.getId(), photo.getImageUrl(), true);
        }
    }
}
