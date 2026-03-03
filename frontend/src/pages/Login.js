import React, { useState } from 'react';
import FaceAuth from '../components/FaceAuth';
import './login.css';

export default function Login() {
  const [message, setMessage] = useState('');
  const [lastMatch, setLastMatch] = useState(null);

  const handleResult = (res) => {
    if (!res || !res.ok) {
      setMessage('No match or no face detected.');
      setLastMatch(null);
      return;
    }
    setMessage(`Matched: ${res.matchLabel} (distance ${res.distance.toFixed(3)})`);
    setLastMatch(res.matchLabel);
    // Here you could call backend to create a session for the matched user
  };

  return (
    <div className="login-container">
      <h2 className="login-header">Login with Face Recognition</h2>
      <p className="login-text">Click the button and allow camera access. The portal will try to match your face with enrolled profiles.</p>
      <div className="face-auth-wrapper">
        <FaceAuth mode="verify" onResult={handleResult} />
      </div>
      <div className="status">
        <strong>Status:</strong> {message}
      </div>
      {lastMatch && (
        <div className="status" style={{marginTop:8}}>
          <button className="login-button" onClick={() => alert(`Proceed to login for ${lastMatch}`)}>Proceed as {lastMatch}</button>
        </div>
      )}
    </div>
  );
}
