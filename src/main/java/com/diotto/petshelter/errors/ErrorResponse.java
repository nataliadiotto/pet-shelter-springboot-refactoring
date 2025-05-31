package com.diotto.petshelter.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Error response")
@Data
@NoArgsConstructor
public class ErrorResponse {

    @Schema(description = "Timestamp of the error", example = "2025-05-31T10:42:15")
    private LocalDateTime timeStamp = LocalDateTime.now();

    @Schema(description = "Detailed error message", example = "Validation failed for object='petDTO'")
    private String message;

    @Schema(description = "Request path", example = "/v1/pets")
    private String url;

    public ErrorResponse(String message, String url) {
        this.message = message;
        this.url = url.replace("uri=", "");
    }
}
