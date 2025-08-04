package com.david.cv_screener_backend.repository;


import com.david.cv_screener_backend.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
