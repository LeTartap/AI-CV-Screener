import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import UploadPage from "./pages/UploadPage";
import RankingPage from "./pages/RankingPage";
import CandidateDetailPage from "./pages/CandidateDetailPage";

function App() {
  return (
    <Router>
      <nav style={{ padding: "10px", background: "#eee" }}>
        <Link to="/" style={{ marginRight: "10px" }}>Upload CV</Link>
        <Link to="/ranking">Ranking</Link>
      </nav>

      <Routes>
        <Route path="/" element={<UploadPage />} />
        <Route path="/ranking" element={<RankingPage />} />
        <Route path="/candidates/:id" element={<CandidateDetailPage />} />
      </Routes>
    </Router>
  );
}

export default App;
