import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

export default function RankingPage() {
  const [candidates, setCandidates] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchRanking = async () => {
      try {
        const res = await axios.get("http://localhost:8080/candidates/ranking");
        // console.log("API raw data:", res.data);
        // setCandidates(res.data);
        console.log("Is array?", Array.isArray(res.data));
        console.log("Length:", res.data.length);
        console.log("First item:", res.data[0]);


        if (Array.isArray(res.data)) {
          setCandidates(res.data);
        } else if (res.data?.content && Array.isArray(res.data.content)) {
          setCandidates(res.data.content);
        } else {
          console.error("Unexpected ranking API format", res.data);
          setCandidates([]);
        }
      } catch (err) {
        console.error("Failed to fetch ranking", err);
        setCandidates([]);
      } finally {
        setLoading(false);
      }
    };

    fetchRanking();
  }, []);

  if (loading) return <p>Loading ranking...</p>;

  return (
    <div style={{ padding: "20px" }}>
      <h1>Candidate Ranking</h1>

      {Array.isArray(candidates) && candidates.length > 0 ? (
        <table border="1" cellPadding="8">
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
