package leets.memoriessquare.domain.photo.usecase;

import leets.memoriessquare.domain.photo.domain.PhotoUpload;
import leets.memoriessquare.domain.photo.repository.PhotoUploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
//import com.amazonaws.services.s3.AmazonS3;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@RequiredArgsConstructor
@Service
public class UploadPhoto {
    private final PhotoUploadRepository photoUploadRepository;

    public PhotoUpload execute(MultipartFile file) throws IOException {
        // Here, you can implement the logic to upload the photo to S3 or any other storage service.
        // After successfully uploading the photo, you can save the photo details in the repository.
        String imageUrl = uploadToS3(file); // Implement the logic to upload to S3

        PhotoUpload photoUpload = PhotoUpload.builder()
                .imageUrl(imageUrl)
                // Set the user if needed
                .build();

        return photoUploadRepository.save(photoUpload);
    }

    private String uploadToS3(MultipartFile file) throws IOException {
        // Create an instance of S3Client
        S3Client s3Client = S3Client.builder().build();

        // Set the bucket name and key for the uploaded file
        String bucketName = "your-s3-bucket-name";
        String key = "photos/" + file.getOriginalFilename();

        // Upload the file to S3
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        PutObjectResponse response = s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // Generate and return the file URL
        return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key)).toExternalForm();
    }

}
