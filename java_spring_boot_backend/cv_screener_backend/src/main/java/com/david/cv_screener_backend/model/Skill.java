package com.david.cv_screener_backend.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skill;
    private Double confidence;

    @ManyToOne
    @JsonIgnore // Prevent circular reference in JSON serialization
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
