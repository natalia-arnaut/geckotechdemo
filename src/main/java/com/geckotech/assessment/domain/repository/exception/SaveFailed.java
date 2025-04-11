package com.geckotech.assessment.domain.repository.exception;

public class SaveFailed extends RuntimeException {
    public SaveFailed(String message) { super(message); }
    public SaveFailed(String message, Throwable cause) { super(message, cause); }
}
