package com.notes.services.storage;

import com.notes.core.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/storage")
public class StorageController extends BaseController {

    @Autowired
    private S3StorageService s3StorageService;

    @RequestMapping(value = "/s3/sign", method = RequestMethod.GET)
    public ResponseEntity<S3PostPolicyModel> getS3Signature() {
        return Ok(s3StorageService.getPostPolicy());
    }
}
