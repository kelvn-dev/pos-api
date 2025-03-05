package net.vinpos.api.service.provider;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import net.vinpos.api.config.AWSPropConfig;
import net.vinpos.api.enums.ContentDisposition;
import net.vinpos.api.exception.VinposException;
import net.vinpos.api.utils.HelperUtils;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

  private final AWSPropConfig awsPropConfig;

  private final S3Presigner s3Presigner;

  /** For uploading */
  public PresignedPutObjectRequest getPresignedUrl(String contentType, ObjectCannedACL acl) {
    try {
      String randomString = HelperUtils.getRandomString();
      String key = String.format("%s/%s", awsPropConfig.getS3().getPrefix(), randomString);

      PutObjectRequest objectRequest =
          PutObjectRequest.builder()
              .bucket(awsPropConfig.getS3().getBucket())
              .key(key)
              .contentType(contentType)
              .acl(acl)
              .build();

      PutObjectPresignRequest presignRequest =
          PutObjectPresignRequest.builder()
              .signatureDuration(Duration.ofMinutes(10))
              .putObjectRequest(objectRequest)
              .build();

      return s3Presigner.presignPutObject(presignRequest);
    } catch (S3Exception e) {
      throw new VinposException(e.getMessage());
    }
  }

  /** For retrieving */
  //  @SneakyThrows(RuntimeException.class)
  public PresignedGetObjectRequest getPresignedUrl(
      String key, ContentDisposition contentDisposition) {
    try {
      GetObjectRequest getObjectRequest =
          GetObjectRequest.builder()
              .bucket(awsPropConfig.getS3().getBucket())
              .key(key)
              .responseContentDisposition(
                  ContentDisposition.ATTACHMENT.equals(contentDisposition)
                      ? "attachment; filename=\"" + key.concat(".png") + "\""
                      : ContentDisposition.INLINE.toString())
              .build();

      GetObjectPresignRequest getObjectPresignRequest =
          GetObjectPresignRequest.builder()
              .signatureDuration(Duration.ofMinutes(10))
              .getObjectRequest(getObjectRequest)
              .build();

      return s3Presigner.presignGetObject(getObjectPresignRequest);
    } catch (S3Exception e) {
      throw new VinposException(e.getMessage());
    }
  }
}
