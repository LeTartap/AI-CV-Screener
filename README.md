
# AI CV Screener & Ranking Bot

[](https://www.google.com/search?q=https://github.com/letartap/ai-cv-screener/actions/workflows/ci-cd.yml)

An intelligent, full-stack application designed to automate and streamline the initial CV screening process. This tool parses candidate resumes, scores them based on relevant skills, and provides a ranked list to recruiters, complete with generated feedback and interview questions.

-----

## 🏗️ Architecture

The application is built on a modern microservices architecture, ensuring a clear separation of concerns between the user interface, business logic, and the AI analysis engine.

```mermaid
sequenceDiagram
    participant User
    participant Frontend (React)
    participant Backend (Spring Boot)
    participant Database (PostgreSQL)

    %% --- Authentication Flow --- %%

    Note over User, Database: 1. Authentication Flow

    User->>+Frontend: Fills out registration form
    Frontend->>+Backend: POST /auth/register (name, email, password)
    Backend->>Backend: Hash password (BCrypt)
    Backend->>+Database: INSERT into users table
    Database-->>-Backend: Confirm user creation
    Backend-->>-Frontend: 200 OK
    Frontend-->>-User: Show "Registration Successful"

    User->>+Frontend: Fills out login form
    Frontend->>+Backend: POST /auth/login (email, password)
    Backend->>Backend: AuthenticationManager validates credentials
    Backend->>+Database: SELECT user by email
    Database-->>-Backend: Return user data
    Backend->>Backend: JwtService generates JWT
    Backend-->>-Frontend: 200 OK (AuthResponse with accessToken)
    Note over Frontend: Store JWT in localStorage
    Frontend-->>-User: Redirect to main application

    %% --- Authenticated API Flow (Example: Get Ranking) --- %%

    Note over User, Database: 2. Authenticated API Flow

    User->>+Frontend: Navigates to Ranking page
    Note over Frontend: Retrieve JWT from localStorage
    Frontend->>+Backend: GET /candidates/ranking <br/> (Header: Authorization: Bearer JWT)
    Backend->>Backend: JwtAuthFilter validates token
    alt Token is Valid
        Backend->>Backend: Set SecurityContext
        Backend->>+Database: SELECT candidates ORDER BY score
        Database-->>-Backend: Return candidate list
        Backend-->>-Frontend: 200 OK (Candidate data)
        Frontend-->>-User: Display ranking table
    else Token is Invalid/Expired
        Backend-->>-Frontend: 401 Unauthorized
        Frontend-->>-User: Redirect to Login page
    end
```

-----

## ✨ Features

  * **CV Upload**: Supports both PDF and DOCX file formats.
  * **Automated Skill Extraction**: Intelligently parses resumes to identify key technical skills (e.g., Java, Spring Boot, React).
  * **Candidate Scoring & Ranking**: Assigns a score to each candidate based on predefined skill weights and provides a ranked list.
  * **AI-Generated Feedback**: Provides constructive feedback for each candidate.
  * **Generated Interview Questions**: Creates relevant technical questions based on the skills found in the CV.
  * **Secure Authentication**: Features JWT-based authentication and authorization, with secure endpoints for user registration and login.
  * **Role-Based Access Control**: Defines user roles (`ADMIN`, `RECRUITER`) to manage permissions.
  * **CI/CD Pipeline**: Automated build and deployment pipeline using GitHub Actions and Docker Compose.

-----

## 🛠️ Technology Stack

| Tier | Technology |
| :--- | :--- |
| **Frontend** | React, Vite, React Router, Axios |
| **Backend** | Java 17, Spring Boot, Spring Security, Spring Data JPA |
| **AI Microservice** | Python, FastAPI, PDFPlumber, python-docx |
| **Database** | PostgreSQL |
| **DevOps** | Docker, Docker Compose, GitHub Actions |

-----

## 🚀 Getting Started

### Prerequisites

  * Java 17+
  * Node.js 20+
  * Python 3.11+
  * Docker and Docker Compose
  * A running PostgreSQL instance

### Local Setup

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/letartap/ai-cv-screener.git
    cd ai-cv-screener
    ```

2.  **Configure Environment Variables:**
    Create a `.env` file in the `java_spring_boot_backend/cv_screener_backend` directory with the following content:

    ```
    DB_USERNAME=<your_postgres_username>
    DB_PASSWORD=<your_postgres_password>
    JWT_SECRET=<your_super_secret_jwt_key>
    ```

3.  **Run the Backend (Spring Boot):**

    ```bash
    cd java_spring_boot_backend/cv_screener_backend
    ./mvnw spring-boot:run
    ```

    The backend will be available at `http://localhost:8080`.

4.  **Run the Python Microservice:**
    In a new terminal:

    ```bash
    cd python_ai_cv_microservice
    pip install -r requirements.txt
    uvicorn app.main:app --reload
    ```

    The microservice will be available at `http://localhost:8000`.

5.  **Run the Frontend (React):**
    In another terminal:

    ```bash
    cd react_frontend/cv-screener-frontend
    npm install
    npm run dev
    ```

    The frontend will be available at `http://localhost:5173`.

-----

## 🔌 API Endpoints

### Authentication

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/auth/register` | Register a new user. |
| `POST` | `/auth/login` | Login and receive a JWT. |

### Candidates (Requires Authentication)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/candidates/upload` | Upload a new CV for analysis. |
| `GET` | `/candidates/ranking` | Get the ranked list of all candidates. |
| `GET` | `/candidates/{id}` | Get detailed information for a specific candidate. |

-----

## 📜 License

This project is licensed under the MIT License.
