package com.mg.insuranceapp;

import com.mg.insuranceapp.rest.CalculationController;
import com.mg.insuranceapp.validations.ConsistentDateParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice(basePackageClasses = CalculationController.class, annotations = ConsistentDateParameters.class)
public class ErrorHandling {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorHandling.class);

    @ExceptionHandler
    public ResponseEntity handle(final ConstraintViolationException ex, final HttpServletRequest req) {
        final ApiError apiError = new ApiError();
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setStatus(BAD_REQUEST.value());
        apiError.setError("bad request");
        apiError.setMessage(ex.getMessage());
        apiError.setPath(req.getRequestURI());

        LOG.error("ValidationError: ", ex);
        return ResponseEntity.badRequest().body(apiError);
    }

    private class ApiError {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        void setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
        }

        public int getStatus() {
            return status;
        }

        void setStatus(int status) {
            this.status = status;
        }

        public String getError() {
            return error;
        }

        void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        void setMessage(String message) {
            this.message = message;
        }

        public String getPath() {
            return path;
        }

        void setPath(String path) {
            this.path = path;
        }
    }
}
