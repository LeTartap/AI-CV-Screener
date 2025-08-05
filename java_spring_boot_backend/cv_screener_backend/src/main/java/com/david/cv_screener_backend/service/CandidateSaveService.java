    package com.david.cv_screener_backend.service;

    import com.david.cv_screener_backend.dto.AnalysisResponse;
    import com.david.cv_screener_backend.model.Candidate;
    import com.david.cv_screener_backend.model.Feedback;
    import com.david.cv_screener_backend.model.InterviewQuestion;
    import com.david.cv_screener_backend.model.Skill;
    import com.david.cv_screener_backend.repository.CandidateRepository;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class CandidateSaveService {

        private final CandidateRepository candidateRepository;

        public CandidateSaveService(CandidateRepository candidateRepository) {
            this.candidateRepository = candidateRepository;
        }

        public Candidate saveFromAnalysis(AnalysisResponse response, String email, String cvPath) {
            Candidate candidate = new Candidate();
            candidate.setName(response.getCandidate());
            candidate.setEmail(email);
            candidate.setScore(response.getScore());
            candidate.setCvPath(cvPath);  // optional: path on disk or cloud

            // Save without children first (to get ID)
            Candidate savedCandidate = candidateRepository.save(candidate);

            // Add skills
            List<Skill> skillEntities = response.getSkills().stream().map(s -> {
                Skill skill = new Skill();
                skill.setSkill(s.getName());
                skill.setConfidence(s.getConfidence());
                skill.setCandidate(savedCandidate); //
                return skill;
            }).toList();

            // Add interview questions
            List<InterviewQuestion> questions = response.getQuestions().stream().map(q -> {
                InterviewQuestion iq = new InterviewQuestion();
                iq.setQuestion(q);
                iq.setCandidate(savedCandidate);
                return iq;
            }).toList();

            // Add feedback
            Feedback fb = new Feedback();
            fb.setMessage(response.getFeedback());
            fb.setCandidate(savedCandidate);

            savedCandidate.setSkills(skillEntities);
            savedCandidate.setInterviewQuestions(questions);
            savedCandidate.setFeedback(List.of(fb));

            // Save everything with cascade
            return candidateRepository.save(savedCandidate);
        }

    }
