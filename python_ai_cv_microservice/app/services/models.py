from pydantic import BaseModel
from typing import List, Dict

class Skill(BaseModel):
    name: str
    confidence: float

class AnalysisResponse(BaseModel):
    candidate: str
    skills: List[Skill]
    score: int
    feedback: str
    questions: List[str]
