package com.factoriaF5.cukies.exception;

import com.factoriaF5.cukies.DTOs.ErrorDTO;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private List<ErrorDTO> errors;
    private LocalDateTime timestamp = LocalDateTime.now();
    private int statusCode;
    private String status;

    public ErrorResponse(List<ErrorDTO> errors, HttpStatus status) {
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
        this.statusCode = status.value();
        this.status = status.name();
    }

}
