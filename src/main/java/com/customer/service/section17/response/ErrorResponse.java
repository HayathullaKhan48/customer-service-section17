package com.customer.service.section17.response;

/**
 * Represents an error response with a status code and a message.
 *
 * @param statusCode the HTTP status code representing the error
 * @param message a descriptive message providing details about the error
 */
public record ErrorResponse(int statusCode, String message) {
}
