package com.jobtracker.common;

import com.jobtracker.common.dto.ApiErrorResponse;
import com.jobtracker.common.exceptions.ConflictException;
import com.jobtracker.common.exceptions.NotFoundException;
import com.jobtracker.common.exceptions.UnauthorisedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UnauthorisedException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorized(
            UnauthorisedException ex,
            HttpServletRequest request
    ) {
        return build(HttpStatus.UNAUTHORIZED, ex.getMessage(), request.getRequestURI(), null);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiErrorResponse> handleConflict(
            ConflictException ex,
            HttpServletRequest request
    ) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI(), null);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            NotFoundException ex,
            HttpServletRequest request
    ) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fe -> fe.getField(),
                        fe -> fe.getDefaultMessage(),
                        (a, b) -> a
                ));

        return build(HttpStatus.BAD_REQUEST, "Validation failed", request.getRequestURI(), fieldErrors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", request.getRequestURI(), null);
    }

    private ResponseEntity<ApiErrorResponse> build(
            HttpStatus status,
            String message,
            String path,
            Map<String, String> fieldErrors
    ) {
        ApiErrorResponse body = new ApiErrorResponse(
                OffsetDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path,
                fieldErrors
        );

        return ResponseEntity.status(status).body(body);
    }
}
