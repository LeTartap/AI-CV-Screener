from typing import List
from app.services.models import Skill

def extract_skills(text: str) -> List[Skill]:
    # Dummy skills (replace with real keyword matching later)
    return [
        Skill(name="Java", confidence=0.95),
        Skill(name="Spring Boot", confidence=0.9),
        Skill(name="SQL", confidence=0.85)
    ]
