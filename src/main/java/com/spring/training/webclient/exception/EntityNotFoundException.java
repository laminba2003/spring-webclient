package com.spring.training.webclient.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EntityNotFoundException extends RuntimeException {
    String message;

    public EntityNotFoundException(String id) {
        message = "user not found with id : "+id;
    }

}
