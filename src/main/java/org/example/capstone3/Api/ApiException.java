package org.example.capstone3.Api;

import lombok.Getter;
import lombok.Setter;

//Hussam created

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
