package com.notes.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    protected ResponseEntity Ok(){
        return new ResponseEntity(HttpStatus.OK);
    }

    protected ResponseEntity NotFound(){
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    protected <T> ResponseEntity<T> Ok(T value){
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    protected <T> ResponseEntity<T> Conflict(T value){
        return new ResponseEntity<>(value, HttpStatus.CONFLICT);
    }

    protected ResponseEntity Conflict(){
        return new ResponseEntity(HttpStatus.CONFLICT);
    }

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
}
