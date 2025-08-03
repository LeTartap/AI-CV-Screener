from typing import List
from app.services.models import Skill
import re

# ✅ Define keywords from your curriculum stack
SKILL_KEYWORDS = {
    "Java": ["java"],
    "Spring Boot": ["spring boot", "springboot"],
    "SQL": ["sql"],
    "PostgreSQL": ["postgresql", "postgres"],
    "Git": ["git"],
    "Testing": ["junit", "mockito", "unit test", "integration test"],
    "React": ["react", "react.js", "reactjs"]
}

def extract_skills(text: str) -> List[Skill]:
    text_lower = text.lower()
    detected_skills = []

    for skill_name, patterns in SKILL_KEYWORDS.items():
        for pattern in patterns:
            if re.search(rf"\b{pattern}\b", text_lower):
                # Confidence is basic for now: 0.9 if found, 0.8 for partial match
                detected_skills.append(Skill(name=skill_name, confidence=0.9))
                break

    return detected_skills
