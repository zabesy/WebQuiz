package com.example.adam.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Completed {

    @Id
    @GeneratedValue
    @JsonIgnore
    private int completedID;

    private int id;

    private LocalDateTime completedAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

}
