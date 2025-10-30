package com.DXCTrainingProject.NoteMakingApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponseDTO {

    private Long id;
    private String noteTitle;
    private String noteDescription;
    private String tag;
    private Boolean isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
