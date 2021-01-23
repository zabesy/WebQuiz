package com.example.adam.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class WebQuiz {

    @Id
    @GeneratedValue
    private int id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String text;

    @Size(min = 2)
    @NotNull
    private String[] options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer = new int[]{};

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

}