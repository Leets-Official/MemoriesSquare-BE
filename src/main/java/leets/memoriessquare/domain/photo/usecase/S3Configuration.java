//package leets.memoriessquare.infrastructure.config;
package leets.memoriessquare.domain.photo.usecase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Configuration {
    @Value("${aws.region}") // application.properties에서 aws.region 값을 읽어옵니다.
    private String region;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region)) // 리전을 지정합니다.
                .build();
    }
}
