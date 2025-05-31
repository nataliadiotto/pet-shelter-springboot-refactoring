package com.diotto.petshelter.errors;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private LocalDateTime timeStamp = LocalDateTime.now();
    private String message;
    private String url;

    public ErrorResponse(String message, String url) {
        this.message = message;
        this.url = url.replace("uri=", "");
    }
}
