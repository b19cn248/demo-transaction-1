package com.opn.demo.controller.advice;

import com.opn.demo.exception.base.BaseException;
import com.opn.demo.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandleAdvice {


  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> handleException(
        BaseException ex
  ){
    return new ResponseEntity<>(
          new ErrorResponse(
                ex.getHttpStatus().value(),
                ex.getMessage()
          )
          ,ex.getHttpStatus()
    );
  }
}
