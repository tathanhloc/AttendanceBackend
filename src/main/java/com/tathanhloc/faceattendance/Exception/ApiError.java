package com.tathanhloc.faceattendance.Exception;

public class ApiError extends RuntimeException {
  public ApiError(String message) {
    super(message);
  }
}
