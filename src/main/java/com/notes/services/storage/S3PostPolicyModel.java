package com.notes.services.storage;

import com.notes.core.BaseType;
import lombok.Data;

@Data
public class S3PostPolicyModel extends BaseType {
    private String postPolicy;
    private String policySignature;
}
