package com.david.cv_screener_backend.repository;


import com.david.cv_screener_backend.model.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {
}
