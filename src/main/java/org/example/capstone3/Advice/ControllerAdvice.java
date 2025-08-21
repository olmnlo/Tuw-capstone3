package org.example.capstone3.Advice;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Api.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
//Hussam created
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ApiResponse> ApiException(ApiException apiException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(apiException.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> MethodArgumentNotValidException (MethodArgumentNotValidException methodArgumentNotValidException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(methodArgumentNotValidException.getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> HttpMessageNotReadableException (HttpMessageNotReadableException httpMessageNotReadableException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(httpMessageNotReadableException.getMessage()));
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> DataIntegrityViolationException (DataIntegrityViolationException dataIntegrityViolationException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(dataIntegrityViolationException.getMessage()));
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<ApiResponse> NoResourceFoundException (NoResourceFoundException noResourceFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(noResourceFoundException.getMessage()));
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> HttpRequestMethodNotSupportedException (HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ApiResponse(httpRequestMethodNotSupportedException.getMessage()));
    }

    @ExceptionHandler(value = TransactionSystemException.class)
    public ResponseEntity<ApiResponse> TransactionSystemException (TransactionSystemException transactionSystemException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(transactionSystemException.getMessage()));
    }

    @ExceptionHandler(value = UnexpectedTypeException.class)
    public ResponseEntity<ApiResponse> UnexpectedTypeException (UnexpectedTypeException unexpectedTypeException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(unexpectedTypeException.getMessage()));
    }

    @ExceptionHandler(value = JpaSystemException.class)
    public ResponseEntity<ApiResponse> JpaSystemException(JpaSystemException jpaSystemException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(jpaSystemException.getMessage()));
    }

    @ExceptionHandler(value = InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<ApiResponse> InvalidDataAccessResourceUsageException (InvalidDataAccessResourceUsageException invalidDataAccessResourceUsageException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(invalidDataAccessResourceUsageException.getMessage()));
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ApiResponse> NullPointerException (NullPointerException nullPointerException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(nullPointerException.getMessage()));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> ConstraintViolationException (ConstraintViolationException constraintViolationException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(constraintViolationException.getMessage()));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> IllegalArgumentException (IllegalArgumentException illegalArgumentException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(illegalArgumentException.getMessage()));
    }
}
