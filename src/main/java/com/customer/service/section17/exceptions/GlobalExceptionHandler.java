package com.customer.service.section17.exceptions;

import com.customer.service.section17.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 *
 * <p>This class intercepts exceptions thrown from any controller
 * and converts them into standardized {@link ErrorResponse} objects
 * with appropriate HTTP status codes.
 *
 * <p>By centralizing exception handling here, we ensure consistent
 * error messages and status codes across all API endpoints.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles cases where a requested customer does not exist in the system.
     *
     * @param e the {@link CustomerNotExistsException} thrown by the service layer
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse}
     *         with HTTP status {@code 404 NOT_FOUND}
     */
    @ExceptionHandler(CustomerNotExistsException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotExists(CustomerNotExistsException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    /**
     * Handles cases where a customer creation attempt fails because the customer already exists.
     *
     * @param e the {@link CustomerAlreadyExistsException} thrown by the service layer
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse}
     *         with HTTP status {@code 409 CONFLICT}
     */
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCustomerAlreadyExists(CustomerAlreadyExistsException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }
}
