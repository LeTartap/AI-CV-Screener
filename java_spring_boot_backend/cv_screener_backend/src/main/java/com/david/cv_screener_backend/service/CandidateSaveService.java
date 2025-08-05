    package com.david.cv_screener_backend.service;

    import com.david.cv_screener_backend.dto.AnalysisResponse;
    import com.david.cv_screener_backend.model.Candidate;
    import com.david.cv_screener_backend.model.Feedback;
    import com.david.cv_screener_backend.model.InterviewQuestion;
    import com.david.cv_screener_backend.model.Skill;
    import com.david.cv_screener_backend.repository.CandidateRepository;
    import org.springframework.data.domain.Sort;
    import org.springframework.stereotype.Service;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.stream.Collectors;

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
            candidate.setCvPath(cvPath);

            Candidate savedCandidate = candidateRepository.save(candidate);

            // Skills
            List<Skill> skillEntities = response.getSkills().stream().map(s -> {
                Skill skill = new Skill();
                skill.setSkill(s.getName());
                skill.setConfidence(s.getConfidence());
                skill.setCandidate(savedCandidate);
                return skill;
            }).collect(Collectors.toCollection(ArrayList::new)); // ✅ mutable list

            // Interview questions
            List<InterviewQuestion> questions = response.getQuestions().stream().map(q -> {
                InterviewQuestion iq = new InterviewQuestion();
                iq.setQuestion(q);
                iq.setCandidate(savedCandidate);
                return iq;
            }).collect(Collectors.toCollection(ArrayList::new)); // ✅ mutable list

            // Feedback
            List<Feedback> feedbackList = new ArrayList<>(); // ✅ mutable
            Feedback fb = new Feedback();
            fb.setMessage(response.getFeedback());
            fb.setCandidate(savedCandidate);
            feedbackList.add(fb);

            savedCandidate.setSkills(skillEntities);
            savedCandidate.setInterviewQuestions(questions);
            savedCandidate.setFeedback(feedbackList);

            return candidateRepository.save(savedCandidate);
        }

        public List<Candidate> getRanking() {
            return candidateRepository.findAll(
                    Sort.by(Sort.Direction.DESC, "score")
            );
        }

    }
