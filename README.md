# AI CV Screener & Ranking Bot

## 📌 Overview
The AI CV Screener is a **full-stack application** that:
- Parses candidate CVs to extract technical skills, relevant coursework, and keywords.
- Uses an AI-powered microservice to **score** and **rank** candidates.
- Stores candidates in a database and provides a ranking API.
- Displays the rankings and candidate details in a React frontend.

---

## 🏗️ Architecture

```plaintext
[Placeholder for Architecture Diagram]
````

*(See `/docs/architecture.puml` for the PlantUML source)*

---

## 🔹 Components

### 1. **Frontend (React + Vite)**

* Pages:

  * **Upload Page** → Upload CV + candidate info
  * **Ranking Page** → View candidate ranking
  * **Candidate Details Page** → View full details, skills, feedback
* Communicates with Spring Boot backend via REST API.

---

### 2. **Backend (Spring Boot)**

* **Controllers**

  * `CandidateController` handles upload, ranking, and candidate retrieval.
* **Services**

  * `CandidateSaveService` saves AI analysis results to the database.
* **Repositories**

  * `CandidateRepository`, `SkillRepository`, `FeedbackRepository`, `InterviewQuestionRepository`
* **Database**

  * PostgreSQL schema `cv_screener` for candidates, skills, feedback, interview questions.
* **Integration**

  * Calls Python microservice (`/analyze`) for CV parsing and AI analysis.

---

### 3. **Python Microservice (FastAPI)**

* **File Parser**

  * Extracts text from uploaded PDF/DOCX CVs.
* **AI Analysis**

  * Extracts skills, calculates score, generates interview questions and feedback.
  * *(Planned)* → Integrate with a free LLM (e.g., Ollama Mistral or Hugging Face model).

---

## 🔌 Data Flow

1. User uploads CV via React frontend.
2. Spring Boot backend stores file temporarily and sends it to Python microservice.
3. Python microservice parses and analyzes the CV, returning structured data.
4. Spring Boot saves the analysis in PostgreSQL.
5. Frontend fetches ranking and displays it.
6. Clicking a candidate shows detailed skills, feedback, and interview questions.

---

## 🚀 Getting Started

### **Backend (Spring Boot)**

```bash
cd java_spring_boot_backend
./mvnw spring-boot:run
```

### **Python Microservice**

```bash
cd python_ai_cv_microservice
pip install -r requirements.txt
uvicorn app.main:app --reload
```

### **Frontend (React + Vite)**

```bash
cd react_frontend\cv-screener-frontend
npm install
npm run dev
```

---

## 🗂️ Project Structure

```
root/
│── cv_screener_backend/       # Spring Boot backend
│── python_ai_cv_microservice/ # FastAPI Python service
│── frontend/                  # React + Vite frontend
│── docs/                      # Documentation (architecture.puml, diagrams, etc.)
```

---

## 📜 License

MIT License
