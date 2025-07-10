package io.github.jimiwrd.workoutservice.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class BaseException extends RuntimeException {

  private final ErrorCode errorCode;
  private final HttpStatus httpStatus;


    public BaseException(String message, ErrorCode errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public ErrorResponse toErrorResponse() {
      return new ErrorResponse(errorCode, getMessage(), List.of());
    }
}
