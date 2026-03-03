import React from 'react';
import './App.css';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Register from './pages/Register';
import Login from './pages/Login';
import FaceRecognition from './pages/FaceRecognition';

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <nav style={{ padding: 20 }}>
          <Link to="/register" style={{ marginRight: 10 }}>Register</Link>
          <Link to="/login" style={{ marginRight: 10 }}>Login</Link>
          <Link to="/face">Face</Link>
        </nav>

        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path="/face" element={<FaceRecognition />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;