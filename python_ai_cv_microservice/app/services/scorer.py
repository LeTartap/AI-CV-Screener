from typing import List
from app.services.models import Skill

# Define weights for skills (backend heavier due to curriculum focus)
SKILL_WEIGHTS = {
    "Java": 25,
    "Spring Boot": 25,
    "SQL": 15,
    "PostgreSQL": 10,
    "Git": 10,
    "Testing": 10,
    "React": 5
}

def calculate_score(skills: List[Skill]) -> int:
    total = 0
    for skill in skills:
        weight = SKILL_WEIGHTS.get(skill.name, 5)
        total += weight * skill.confidence

    # Normalize to 0-100
    max_score = sum(SKILL_WEIGHTS.values())
    return int((total / max_score) * 100)
