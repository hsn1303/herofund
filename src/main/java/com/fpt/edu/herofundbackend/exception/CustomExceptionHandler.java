package com.fpt.edu.herofundbackend.exception;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.dto.CommonResponse;
import com.fpt.edu.herofundbackend.util.MyStringUtils;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.FAIL);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        response.setData(errors);
        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MyNotFoundException.class)
    public CommonResponse handleMyNotFoundExceptions(MyNotFoundException ex) {
        CommonResponse response = new CommonResponse(false, SystemConstant.Message.FAIL);
        response.setFailResponse(MyStringUtils.isNullOrEmptyWithTrim(ex.getMessage()) ?
                HttpStatus.NOT_FOUND.name() : ex.getMessage());
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResponse handleConstraintViolationExceptions(ConstraintViolationException ex) {
        CommonResponse response = new CommonResponse();
        Map<String, String> map = new HashMap<>();
        String message = MyStringUtils.isNullOrEmptyWithTrim(ex.getLocalizedMessage()) ?
                HttpStatus.BAD_REQUEST.name() : ex.getLocalizedMessage();
        if (!MyStringUtils.isNullOrEmptyWithTrim(message)) {
            List<String> items = Arrays.asList(message.split("\\s*,\\s*"));
            items.forEach(s -> {
                StringBuilder builder = new StringBuilder(s);
                int index = builder.indexOf(".");
                builder.replace(0, index + 1, "");
                int indexLan2 = builder.indexOf(":");
                String field = builder.substring(0, indexLan2);
                String value = builder.substring(indexLan2 + 1, builder.length());
                map.put(field, value.trim());
            });
            response.setValidateErrorsResponse(map);
        }else {
            response.setFailResponse(message);
        }

        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public CommonResponse handleUsernameNotFoundExceptions(UsernameNotFoundException ex) {
        CommonResponse response = new CommonResponse();
        response.setFailResponse(MyStringUtils.isNullOrEmptyWithTrim(ex.getMessage()) ?
                HttpStatus.BAD_REQUEST.name() : ex.getMessage());
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MyBadRequestException.class)
    public CommonResponse handleBadRequestExceptionExceptions(MyBadRequestException ex) {
        CommonResponse response = new CommonResponse();
        response.setFailResponse(MyStringUtils.isNullOrEmptyWithTrim(ex.getMessage()) ?
                HttpStatus.BAD_REQUEST.name() : ex.getMessage());
        return response;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MySystemException.class)
    public CommonResponse handleSystemExceptions(MySystemException ex) {
        CommonResponse response = new CommonResponse();
        response.setFailResponse(MyStringUtils.isNullOrEmptyWithTrim(ex.getMessage()) ?
                HttpStatus.INTERNAL_SERVER_ERROR.name() : ex.getMessage());
        return response;
    }

}
