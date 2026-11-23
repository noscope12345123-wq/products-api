package uk.ac.westminster.products_api.exception;

import java.time.LocalDateTime;

/**
 * The JSON body of every error response.
 * Jackson builds the JSON from the getters - without them the body would be {}.
 */
public class ErrorResponse {

    private final String message;
    private final int status;
    private final LocalDateTime timestamp;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
