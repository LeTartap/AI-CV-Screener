from typing import List
from app.services.models import Skill

def calculate_score(skills: List[Skill]) -> int:
    # Simple dummy scoring: average confidence * 100
    if not skills:
        return 0
    avg_conf = sum(s.confidence for s in skills) / len(skills)
    return int(avg_conf * 100)
