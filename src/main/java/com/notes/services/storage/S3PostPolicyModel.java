package com.notes.services.storage;

import lombok.Data;

@Data
public class S3PostPolicyModel {
    private String postPolicy;
    private String policySignature;
}
