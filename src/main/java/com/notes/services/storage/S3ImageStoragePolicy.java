package com.notes.services.storage;

import com.amazonaws.services.ec2.util.S3UploadPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class S3ImageStoragePolicy {

    @Value("${spring.aws.s3.key}")
    private String keyId;

    @Value("${spring.aws.s3.secret}")
    private String keySecret;

    @Value("${spring.aws.s3.bucket}")
    private String bucketName;

    @Value("${spring.aws.s3.expireInMin}")
    private Integer policyExpireMin;

    private S3UploadPolicy uploadPolicy;

    @Autowired
    public S3ImageStoragePolicy() { }

    S3PostPolicyModel getPostPolicy(Long userId) {

        String prefix = String.format("images/%d", userId);
        uploadPolicy = new S3UploadPolicy(keyId, keySecret, bucketName, prefix, policyExpireMin);

        return new S3PostPolicyModel() {{
            setPostPolicy(uploadPolicy.getPolicyString());
            setPolicySignature(uploadPolicy.getPolicySignature());
        }};
    }

}
