import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const res = await axios.post("http://localhost:8080/auth/login", {
        email,
        password,
      });

      // Store the token
      localStorage.setItem("accessToken", res.data.accessToken);

      // Redirect to the ranking page after login
      navigate("/ranking");

    } catch (err) {
      console.error("Login failed", err);
      setError("Login failed. Please check your credentials.");
    }
  };

   return (
    <div className="container">
      <h1>Login</h1>
      <form onSubmit={handleSubmit} className="form-container">
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">Login</button>
        {error && <p style={{ color: "red" }}>{error}</p>}
      </form>
    </div>
  );
}