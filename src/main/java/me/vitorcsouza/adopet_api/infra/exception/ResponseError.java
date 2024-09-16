package me.vitorcsouza.adopet_api.infra.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ResponseError(String message, HttpStatus httpStatus, LocalDateTime time) {
}
