from typing import List
from app.services.models import Skill

def generate_feedback(skills: List[Skill]) -> str:
    skill_names = [s.name for s in skills]
    if "React" not in skill_names:
        return "Strong backend skills. Improve React basics to increase chances."
    return "Great full-stack skillset!"

def generate_questions(skills: List[Skill]) -> List[str]:
    questions = []
    if any(s.name == "Spring Boot" for s in skills):
        questions.append("Explain dependency injection in Spring Boot.")
    if any(s.name == "SQL" for s in skills):
        questions.append("How do you optimize PostgreSQL queries for performance?")
    return questions
