import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import axios from "axios";

export default function CandidateDetailPage() {
  const { id } = useParams();
  const [candidate, setCandidate] = useState(null);

  useEffect(() => {
    const fetchCandidate = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/candidates/${id}`);
        setCandidate(res.data);
      } catch (err) {
        console.error("Failed to fetch candidate details", err);
      }
    };
    fetchCandidate();
  }, [id]);

  if (!candidate) return <p>Loading candidate details...</p>;

  return (
    <div style={{ padding: "20px" }}>
      <Link to="/ranking">← Back to Ranking</Link>
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
