package com.david.cv_screener_backend.model;
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
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}
