package leets.memoriessquare.global.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3Client;
    private static final String S3_BUCKET_NAME = "memoriessquare";

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = generateFileName(file);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        Map<String, String> metadataMap = new HashMap<>();
        metadataMap.put("Content-Type", metadata.getContentType());
        metadataMap.put("Content-Length", String.valueOf(metadata.getContentLength()));

        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(S3_BUCKET_NAME)
                    .key(fileName)
                    .metadata(metadataMap)
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(inputStream, file.getSize()));

            return generateImageUrl(fileName);
        } catch (SdkClientException e) {
            throw new IOException("Failed to upload image to S3", e);
        }
    }

    private String generateFileName(MultipartFile file) {
        String extension = getFileExtension(file);
        String randomName = UUID.randomUUID().toString();
        return randomName + extension;
    }

    private String getFileExtension(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName != null) {
            int dotIndex = originalFileName.lastIndexOf('.');
            if (dotIndex >= 0 && dotIndex < originalFileName.length() - 1) {
                return originalFileName.substring(dotIndex);
            }
        }
        return "";
    }

    private String generateImageUrl(String fileName) {
        return s3Client.utilities().getUrl(builder -> builder.bucket(S3_BUCKET_NAME).key(fileName)).toExternalForm();
    }
}
