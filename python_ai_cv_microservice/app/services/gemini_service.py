import os
import google.generativeai as genai
from typing import List
from .models import Skill

# Configure the Gemini API client
# Make sure to set the GOOGLE_API_KEY environment variable
genai.configure(api_key=os.environ["GOOGLE_API_KEY"])

# Initialize the Gemini model
model = genai.GenerativeModel('gemini-1.5-flash')

def generate_gemini_feedback(text: str, skills: List[Skill]) -> str:
    """
    Generates personalized feedback for a candidate using Gemini.
    """
    skill_list = ", ".join([f"{s.name} ({s.confidence*100:.0f}% confidence)" for s in skills])

    prompt = f"""
    As an expert technical recruiter, provide concise, constructive feedback for a candidate
    based on the following extracted CV text and detected skills. The ideal candidate has strong
    backend skills in Java, Spring Boot, and SQL, with some familiarity with React.

    CV Text:
    ---
    {text}
    ---

    Detected Skills: {skill_list}

    Feedback:
    """
    try:
        response = model.generate_content(prompt)
        return response.text.strip()
    except Exception as e:
        print(f"Error generating Gemini feedback: {e}")
        return "Could not generate AI feedback at this time."


def generate_gemini_questions(skills: List[Skill]) -> List[str]:
    """
    Generates interview questions based on detected skills using Gemini.
    Returns a list of 2-3 questions.
    """
    skill_list = ", ".join([s.name for s in skills])

    prompt = f"""
    As an expert technical interviewer, generate 2-3 insightful interview questions
    based on the following list of skills: {skill_list}.
    Focus on practical application and problem-solving.

    Return the questions as a comma-separated list. For example:
    Question 1, Question 2, Question 3

    Questions:
    """
    try:
        response = model.generate_content(prompt)
        # Split the comma-separated string of questions into a list
        questions = [q.strip() for q in response.text.strip().split(',') if q.strip()]
        return questions
    except Exception as e:
        print(f"Error generating Gemini questions: {e}")
        return ["Could not generate AI questions at this time."]