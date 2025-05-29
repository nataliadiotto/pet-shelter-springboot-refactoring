package com.diotto.petshelter.errors;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private Date date = new Date();
    private String message;
    private String url;

    public ErrorResponse(String message, String url) {
        this.message = message;
        this.url = url.replace("uri=", "");
    }
}
