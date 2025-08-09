import { useEffect, useState } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import axios from "axios";

export default function CandidateDetailPage() {
  const { id } = useParams();
  const [candidate, setCandidate] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCandidate = async () => {
      try {
        const token = localStorage.getItem("accessToken");
        if (!token) {
          navigate("/login");
          return;
        }

        const res = await axios.get(`http://localhost:8080/candidates/${id}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        setCandidate(res.data);
      } catch (err) {
        if (err.response && (err.response.status === 401 || err.response.status === 403)) {
            navigate("/login");
        } else {
            console.error("Failed to fetch candidate details", err);
        }
      }
    };
    fetchCandidate();
  }, [id, navigate]);

  if (!candidate) return <p>Loading candidate details...</p>;
  return (
    <div className="container">
      <Link to="/ranking" className="back-link">← Back to Ranking</Link>
      <h1>{candidate.name}</h1>
      <p>Email: {candidate.email}</p>
      <p>Score: {candidate.score}</p>
      <h2>Skills</h2>
      <ul>
        {candidate.skills?.map((s) => (
          <li key={s.id}>{s.skill} ({(s.confidence * 100).toFixed(1)}%)</li>
        ))}
      </ul>

      <h2>Feedback</h2>
      <ul>
        {candidate.feedback?.map((f) => (
          <li key={f.id}>{f.message}</li>
        ))}
      </ul>

      <h2>Interview Questions</h2>
      <ul>
        {candidate.interviewQuestions?.map((q) => (
          <li key={q.id}>{q.question}</li>
        ))}
      </ul>
    </div>
  );
}