package com.example.groupId.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Факультет не найден")
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(long id) {
        super("Студент:%s не найден".formatted(id));
    }
}