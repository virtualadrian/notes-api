package com.notes.services.storage;

import com.notes.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S3StorageService {

    @Autowired
    private S3ImageStoragePolicy s3ImageStoragePolicy;

    S3PostPolicyModel getPostPolicy() {
        return s3ImageStoragePolicy.getPostPolicy(SecurityUtil.getCurrentUserAccountId());
    }

}
