package com.DXCTrainingProject.NoteMakingApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDTO {
    private String path;
    private HttpStatus statusCode;
    private String errorMessage;
    private LocalDateTime errorTime;
}
