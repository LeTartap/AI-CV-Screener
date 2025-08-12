import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function UploadPage({ onUploadComplete }) {
  const [file, setFile] = useState(null);
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [result, setResult] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("Form submitted");
    if (!file) {
      alert("Please select a file");
      return;
    }
    
    const token = localStorage.getItem("accessToken");
    if (!token) {
      navigate("/login");
      return;
    }
    
    try {
      const formData = new FormData();
      formData.append("file", file);
      formData.append("name", name);
      formData.append("email", email);

      const res = await axios.post("http://localhost:8080/candidates/upload", formData, {
        headers: { 
          "Content-Type": "multipart/form-data",
          "Authorization": `Bearer ${token}`
        },
      });
      setResult(res.data);
      if (onUploadComplete) onUploadComplete();
    } catch (err) {
      console.error(err);
      if (err.response && (err.response.status === 401 || err.response.status === 403)) {
        // Token invalid or expired, redirect to login
        localStorage.removeItem("accessToken");
        navigate("/login");
      } else {
        alert("Upload failed");
      }
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Upload CV</h1>
      <form onSubmit={handleSubmit}>
        <input type="file" onChange={(e) => setFile(e.target.files[0])} />
        <input type="text" placeholder="Name" onChange={(e) => setName(e.target.value)} />
        <input type="email" placeholder="Email" onChange={(e) => setEmail(e.target.value)} />
        <button type="submit">Upload</button>
      </form>
      {result && (
        <div style={{ marginTop: "20px", padding: "10px", backgroundColor: "#e8f5e8", border: "1px solid #4caf50", borderRadius: "4px" }}>
          <h3>Upload Successful!</h3>
          <p>Candidate: {result.name}</p>
          <p>Score: {result.score}</p>
        </div>
      )}
    </div>
  );
}
