package com.david.cv_screener_backend.repository;


import com.david.cv_screener_backend.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
