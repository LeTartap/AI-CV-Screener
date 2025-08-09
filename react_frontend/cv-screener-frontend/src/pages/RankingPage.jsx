import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

export default function RankingPage() {
  const [candidates, setCandidates] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchRanking = async () => {
      try {
        const token = localStorage.getItem("accessToken");
        if (!token) {
          navigate("/login"); // Redirect if not logged in
          return;
        }

        const res = await axios.get(
          "http://localhost:8080/candidates/ranking",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (Array.isArray(res.data)) {
          setCandidates(res.data);
        } else {
          console.error("Unexpected ranking API format", res.data);
          setCandidates([]);
        }
      } catch (err) {
        if (
          err.response &&
          (err.response.status === 401 || err.response.status === 403)
        ) {
          navigate("/login"); // Redirect on auth error
        } else {
          console.error("Failed to fetch ranking", err);
        }
        setCandidates([]);
      } finally {
        setLoading(false);
      }
    };

    fetchRanking();
  }, [navigate]);

  if (loading) return <p>Loading ranking...</p>;
  return (
    <div className="container">
      <h1>Candidate Ranking</h1>
      {Array.isArray(candidates) && candidates.length > 0 ? (
        <table className="ranking-table">
          <thead>
            <tr>
              <th>Rank</th>
              <th>Name</th>
              <th>Email</th>
              <th>Score</th>
            </tr>
          </thead>
          <tbody>
            {candidates.map((c, index) => (
              <tr key={c.id}>
                <td>{index + 1}</td>
                <td>
                  <Link to={`/candidates/${c.id}`}>{c.name}</Link>
                </td>
                <td>{c.email}</td>
                <td>{c.score}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No candidates found</p>
      )}
    </div>
  );
}
