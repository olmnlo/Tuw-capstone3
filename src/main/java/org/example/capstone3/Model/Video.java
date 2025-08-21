package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//Hussam created
@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String fileName;
    private String contentType;

    private String videoType;

    private String description;

    private String youtubeLink;


    private String filePath;

    @ManyToOne
    @JsonIgnore
    private Plan plan;
}
