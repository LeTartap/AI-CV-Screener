import { BrowserRouter as Router, Routes, Route, Link, useNavigate } from "react-router-dom";
import UploadPage from "./pages/UploadPage";
import RankingPage from "./pages/RankingPage";
import CandidateDetailPage from "./pages/CandidateDetailPage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import ProtectedRoute from "./components/ProtectedRoute"; // Import ProtectedRoute
import "./App.css"; // Make sure to import the CSS file

// A simple component to handle logout logic
function LogoutButton() {
  const navigate = useNavigate();
  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    navigate("/login");
  };
  return <button onClick={handleLogout} style={{ marginLeft: '10px' }}>Logout</button>;
}

function App() {
  return (
 <Router>
      <nav className="navbar">
        <Link to="/">Upload CV</Link>
        <Link to="/ranking">Ranking</Link>
        <Link to="/login">Login</Link>
        <Link to="/register">Register</Link>
        <LogoutButton />
      </nav>

      <Routes>
        {/* Public Routes */}
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        {/* Protected Routes */}
        <Route path="/" element={
          <ProtectedRoute>
            <UploadPage />
          </ProtectedRoute>
        } />
        <Route path="/ranking" element={
          <ProtectedRoute>
            <RankingPage />
          </ProtectedRoute>
        } />
        <Route path="/candidates/:id" element={
          <ProtectedRoute>
            <CandidateDetailPage />
          </ProtectedRoute>
        } />
      </Routes>
    </Router>
  );
}

export default App;