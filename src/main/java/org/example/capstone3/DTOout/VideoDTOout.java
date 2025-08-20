package org.example.capstone3.DTOout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VideoDTOout {

    private Integer id;
    private String fileName;
    private String contentType;
    private String videoType;
}
