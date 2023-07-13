/**
 * Handles all errors that occur in the controller.
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.07
 */
package kr.ac.yonsei.maist.global.error;

import io.jsonwebtoken.ExpiredJwtException;
import kr.ac.yonsei.maist.global.error.exception.InvalidTokenException;
import kr.ac.yonsei.maist.global.error.exception.UnauthorizedException;
import kr.ac.yonsei.maist.global.response.ErrorResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.openmbean.KeyAlreadyExistsException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle exceptions that occur when there is no corresponding value.
     * @param e IllegalArgumentException
     * @return Response message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponseMessage> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("handleIllegalArgumentException", e);

        ErrorResponseMessage responseMessage = ErrorResponseMessage.builder()
                .id("200")
                .message("Invalid Input Value")
                .build();

        return new ResponseEntity<ErrorResponseMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Handle exceptions that occur when there is a value that already exists.
     * @param e KeyAlreadyExistsException
     * @return Response message
     */
    @ExceptionHandler(KeyAlreadyExistsException.class)
    protected ResponseEntity<ErrorResponseMessage> handleKeyAlreadyExistsException(KeyAlreadyExistsException e) {
        log.error("handleKeyAlreadyExistsException", e);

        ErrorResponseMessage responseMessage = ErrorResponseMessage.builder()
                .id("200")
                .message("Duplicate Value")
                .build();

        return new ResponseEntity<ErrorResponseMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Handle exceptions that occur when essential values are not sent.
     * @param e MethodArgumentNotValidException
     * @return Response message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);

        ErrorResponseMessage responseMessage = ErrorResponseMessage.builder()
                .id("200")
                .message("'"+e.getBindingResult().getFieldError().getField()+"' Empty")
                .build();

        return new ResponseEntity<ErrorResponseMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Handle exceptions that occur when the required parameter value is empty.
     * @param e MissingServletRequestParameterException
     * @return Response message
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponseMessage> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("handleMissingServletRequestParameterException", e);

        ErrorResponseMessage responseMessage = ErrorResponseMessage.builder()
                .id("200")
                .message("'"+e.getParameterName()+"' Empty")
                .build();

        return new ResponseEntity<ErrorResponseMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Handle exceptions that occurs when incorrect authentication is performed.
     * @param e UnauthorizedException
     * @return Response message
     */
    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ErrorResponseMessage> handleUnauthorizedException(UnauthorizedException e) {
        log.error("handleUnauthorizedException", e);

        ErrorResponseMessage responseMessage = ErrorResponseMessage.builder()
                .id("200")
                .message("Invalid Authentication")
                .build();

        return new ResponseEntity<ErrorResponseMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Handle exceptions that occurs when token value is invalid.
     * @param e InvalidTokenException
     * @return Response message
     */
    @ExceptionHandler(InvalidTokenException.class)
    protected ResponseEntity<ErrorResponseMessage> handleInvalidTokenException(InvalidTokenException e) {
        log.error("handleInvalidTokenException ", e);

        ErrorResponseMessage responseMessage = ErrorResponseMessage.builder()
                .id("200")
                .message("Empty token value")
                .build();

        return new ResponseEntity<ErrorResponseMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Handle exceptions that occurs when the token has expired.
     * @param e ExpiredJwtException
     * @return Response message
     */
    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ErrorResponseMessage> handleExpiredJwtException(ExpiredJwtException e) {
        log.error("handleExpiredJwtException ", e);

        ErrorResponseMessage responseMessage = ErrorResponseMessage.builder()
                .id("200")
                .errorCode(401)
                .message("Expired token")
                .build();

        return new ResponseEntity<ErrorResponseMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Handle exceptions that occur when requesting a method of an unsupported type.
     * @param e HttpRequestMethodNotSupportedException
     * @return Response message
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponseMessage> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);

        ErrorResponseMessage responseMessage = ErrorResponseMessage.builder()
                .id("200")
                .message("Method Not Allowed")
                .build();

        return new ResponseEntity<ErrorResponseMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Handle exceptions that occur when the required request body is missing.
     * @param e HttpMessageNotReadableException
     * @return Response message
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponseMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("handleHttpMessageNotReadableException", e);

        ErrorResponseMessage responseMessage = ErrorResponseMessage.builder()
                .id("200")
                .message("Required request body is missing")
                .build();

        return new ResponseEntity<ErrorResponseMessage>(responseMessage, HttpStatus.OK);
    }

    /**
     * Handle all errors.
     * @param e Exception
     * @return Response message
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseMessage> handleException(Exception e) {
        log.error("handleEntityNotFoundException", e);

        ErrorResponseMessage responseMessage = ErrorResponseMessage.builder()
                .id("200")
                .message(e.getMessage())
                .build();

        return new ResponseEntity<ErrorResponseMessage>(responseMessage, HttpStatus.OK);
    }
}
