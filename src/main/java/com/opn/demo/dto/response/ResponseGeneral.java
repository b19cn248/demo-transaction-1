package com.opn.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Setter
public class ResponseGeneral<T> {
  private HttpStatus status;
  private String message;
  private T data;

  public static <T> ResponseGeneral<T> ofSuccess(String message , T data){
    return of(HttpStatus.OK, message, data);
  }

  public static <T> ResponseGeneral<T> ofSuccess(String message ){
    return of(HttpStatus.OK, message, null);
  }

}
