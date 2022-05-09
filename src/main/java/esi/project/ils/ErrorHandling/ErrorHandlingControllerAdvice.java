package esi.project.ils.ErrorHandling;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        List<Violation> error = new ArrayList<>();

        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }

        return new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<Violation> error = new ArrayList<>();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorMessage resourceNotFoundExceptionHandler(Exception ex) {
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ErrorMessage globalExceptionHandler(Exception ex) {
        return new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage());
    }

}
