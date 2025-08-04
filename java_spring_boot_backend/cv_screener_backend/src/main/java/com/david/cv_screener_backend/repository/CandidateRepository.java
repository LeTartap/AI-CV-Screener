package com.david.cv_screener_backend.repository;


import com.david.cv_screener_backend.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
