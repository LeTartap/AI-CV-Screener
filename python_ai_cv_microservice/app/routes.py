from fastapi import APIRouter, UploadFile, File, Form
from app.services import parser, skill_extractor, scorer, feedback
from app.services.models import AnalysisResponse

router = APIRouter()

@router.post("/analyze", response_model=AnalysisResponse)
async def analyze_cv(file: UploadFile = File(...), candidate_name: str = Form(None), email: str = Form(None)):
    # 1. Extract text from CV
    text = await parser.extract_text(file)

    # 2. Detect skills
    detected_skills = skill_extractor.extract_skills(text)

    # 3. Score candidate
    score = scorer.calculate_score(detected_skills)

    # 4. Generate feedback & questions
    feedback_msg = feedback.generate_feedback(detected_skills)
    questions = feedback.generate_questions(detected_skills)

    return AnalysisResponse(
        candidate=candidate_name or file.filename,
        skills=detected_skills,
        score=score,
        feedback=feedback_msg,
        questions=questions
    )
