package com.pickdate.bootstrap.infrastructure.config;

import com.pickdate.shared.exception.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.Optional;

import static com.pickdate.shared.exception.ProblemFactory.*;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
class ExceptionAdvice {

    private final Optional<ProblemEventPublisher> problemEventPublisher;

    @ExceptionHandler({
            ErrorResponseException.class,
            org.springframework.web.servlet.resource.NoResourceFoundException.class,
            org.springframework.web.reactive.resource.NoResourceFoundException.class
    })
    ResponseEntity<Problem> handleException(ErrorResponse errorResponse, HttpServletRequest request) {
        log.error("Error response");
        URI uri = getUri(request);
        Problem problem = ProblemFactory.resolveException(errorResponse, uri);
        publish(new ProblemCapturedEvent(problem, errorResponse.getDetailMessageCode()));
        return new ResponseEntity<>(problem, errorResponse.getStatusCode());
    }

    @ApiResponse(responseCode = "404", description = "Not found")
    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<Problem> handleNotFound(RuntimeException ex, HttpServletRequest request) {
        log.info("Resource not found", ex);
        URI uri = getUri(request);
        Problem problem = notFound(ex, uri);
        publish(new ProblemCapturedEvent(problem, ex.getMessage()));
        return new ResponseEntity<>(problem, HttpStatusCode.valueOf(problem.getStatus()));
    }

    @ApiResponse(responseCode = "404", description = "Not found")
    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<Problem> handleNotFound(NotFoundException ex, HttpServletRequest request) {
        log.info("Resource not found", ex);
        URI uri = getUri(request);
        Problem problem = notFound(ex, uri);
        publish(new ProblemCapturedEvent(problem, ex.getMessage()));
        return new ResponseEntity<>(problem, HttpStatusCode.valueOf(problem.getStatus()));
    }

    @ApiResponse(responseCode = "400", description = "Validation exception")
    @ExceptionHandler(IllegalValueException.class)
    ResponseEntity<Problem> handleBadRequest(IllegalValueException ex, HttpServletRequest request) {
        log.warn("Validation exception", ex);
        URI uri = getUri(request);
        Problem problem = badRequest(ex, uri);
        publish(new ProblemCapturedEvent(problem, ex.getMessage()));
        return ResponseEntity.status(HttpStatusCode.valueOf(problem.getStatus())).body(problem);
    }

    @ApiResponse(responseCode = "400", description = "Validation exception")
    @ExceptionHandler({MethodArgumentNotValidException.class, MissingRequestHeaderException.class, IllegalArgumentException.class})
    ResponseEntity<Problem> handleBadRequest(Exception ex, HttpServletRequest request) {
        log.warn("Validation exception", ex);
        URI uri = getUri(request);
        Problem problem = badRequest(ex, uri);
        publish(new ProblemCapturedEvent(problem, ex.getMessage()));
        return ResponseEntity.status(HttpStatusCode.valueOf(problem.getStatus())).body(problem);
    }

    @ApiResponse(responseCode = "409", description = "Resource already exists")
    @ExceptionHandler(ResourceAlreadyExistException.class)
    ResponseEntity<Problem> handleConflict(ResourceAlreadyExistException ex, HttpServletRequest request) {
        log.warn("Resource already exists", ex);
        URI uri = getUri(request);
        Problem problem = conflict(ex, uri);
        publish(new ProblemCapturedEvent(problem, ex.getMessage()));
        return ResponseEntity.status(HttpStatusCode.valueOf(problem.getStatus())).body(problem);
    }

    @ApiResponse(responseCode = "500", description = "Server exception")
    @ExceptionHandler({InternalServerError.class})
    ResponseEntity<Problem> handleInternalServerException(InternalServerError ex, HttpServletRequest request) {
        log.error("Internal server error", ex);
        URI uri = getUri(request);
        Problem problem = internalServerError(ex, uri);
        publish(new ProblemCapturedEvent(problem, ex.getMessage()));
        return new ResponseEntity<>(problem, HttpStatusCode.valueOf(problem.getStatus()));
    }

    @ApiResponse(responseCode = "500", description = "Server exception")
    @ExceptionHandler(Exception.class)
    ResponseEntity<Problem> handleInternalServerException(Exception ex, HttpServletRequest request) {
        log.error("Internal server error", ex);
        URI uri = getUri(request);
        Problem problem = internalServerError(uri);
        publish(new ProblemCapturedEvent(problem, ex.getMessage()));
        return new ResponseEntity<>(problem, HttpStatusCode.valueOf(problem.getStatus()));
    }

    private void publish(ProblemCapturedEvent event) {
        problemEventPublisher.ifPresent(publisher -> publisher.publish(event));
    }

    private URI getUri(HttpServletRequest request) {
        return URI.create(request.getRequestURL().toString());
    }
}
