package com.example.celebrity_management.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.celebrity_management.Exception.AlreadyExistException;
import com.example.celebrity_management.Exception.ForbiddenDataException;
import com.example.celebrity_management.Exception.InvalidDataException;
import com.example.celebrity_management.Exception.ResourceNotFoundException;
import com.example.celebrity_management.Exception.UnauthorizedException;
import com.example.celebrity_management.Exception.ValidationError;
import com.example.celebrity_management.dto.ErrorResponseView;

import lombok.extern.slf4j.Slf4j;


@RestControllerAdvice
@Slf4j
public class RestExceptionHandlerConfig extends ResponseEntityExceptionHandler {

    @Autowired
    private Environment env;


    @ExceptionHandler({ AlreadyExistException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponseView> handleException(final AlreadyExistException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponseView(ex.getMessage()), HttpStatus.CONFLICT);
    }

    // @Override
    // protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
    //         HttpHeaders headers, HttpStatus status, WebRequest request) {
    //    final ValidationError error = fromBindingErrors(ex.getBindingResult());
    //     return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    // }

    @ExceptionHandler({ ResourceNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseView> handleException(final ResourceNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponseView(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ UnauthorizedException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponseView> handleException(final UnauthorizedException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponseView(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ InvalidDataException.class })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponseView> handleException(final InvalidDataException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponseView(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ ForbiddenDataException.class })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponseView> handleException(final ForbiddenDataException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponseView(ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseView> handleException(final Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponseView(getResponseMessage(ex)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // @ExceptionHandler({ AddressException.class })
    // @ResponseStatus(HttpStatus.CONFLICT)
    // public ResponseEntity<ErrorResponseView> handleException(final AddressException ex) {
    //     log.error(ex.getMessage(), ex);
    //     return new ResponseEntity<>(new ErrorResponseView("Error Ocurred while sending mail"), HttpStatus.CONFLICT);
    // }

    // @ExceptionHandler({ MessagingException.class })
    // @ResponseStatus(HttpStatus.CONFLICT)
    // public ResponseEntity<ErrorResponseView> handleException(final MessagingException ex) {
    //     log.error(ex.getMessage(), ex);
    //     return new ResponseEntity<>(new ErrorResponseView("Error Ocurred while sending mail"), HttpStatus.CONFLICT);
    // }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseView> handleException(final DataIntegrityViolationException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponseView("Phone Already Exists"), HttpStatus.BAD_REQUEST);
    }

    public static ValidationError fromBindingErrors(final Errors errors) {
        final ValidationError error = new ValidationError("Validation failed. " + errors.getErrorCount() + " error(s)");
        for (final ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }

    private String getResponseMessage(Exception e){
         if(env.acceptsProfiles(Profiles.of("dev"))){
             return e.getMessage();
         }
         return "Something went wrong. Please try again later.";
     }

}
