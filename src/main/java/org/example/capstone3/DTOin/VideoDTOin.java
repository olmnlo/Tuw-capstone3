package org.example.capstone3.DTOin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VideoDTOin {
    @NotNull(message = "description is required")
    private String description;

    private String youtubeLink;
}
