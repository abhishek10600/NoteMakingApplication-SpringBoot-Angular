package com.DXCTrainingProject.NoteMakingApplication.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {

    @NotBlank
    @Size(min = 3, message = "Title must be at least 3 characters.")
    private String noteTitle;

    @NotBlank
    @Size(min = 5, message = "Description must be at least 5 characters")
    private String noteDescription;

    private String tag;
}
