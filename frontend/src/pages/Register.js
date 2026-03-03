import React, { useState } from 'react';
import FaceAuth from '../components/FaceAuth';

export default function Register() {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [status, setStatus] = useState('');
  const [enrolling, setEnrolling] = useState(false);

  const handleEnrolled = (label, descriptor) => {
    // store in localStorage (demo). Later send to backend.
    const profiles = JSON.parse(localStorage.getItem('face_profiles') || '[]');
    profiles.push({ label, descriptor });
    localStorage.setItem('face_profiles', JSON.stringify(profiles));
    setStatus('Enrollment saved locally.');
    setEnrolling(false);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!name) return alert('Enter name');
    setEnrolling(true);
    setStatus('Ready to capture face — click "Start Enrollment"');
  };

  return (
    <div style={{padding:20}}>
      <h2>Register (create account and enroll face)</h2>
      <form onSubmit={handleSubmit} style={{marginBottom:12}}>
        <div>
          <label>Name: </label>
          <input value={name} onChange={e=>setName(e.target.value)} />
        </div>
        <div>
          <label>Email: </label>
          <input value={email} onChange={e=>setEmail(e.target.value)} />
        </div>
        <div style={{marginTop:8}}>
          <button type="submit">Create & Enroll Face</button>
        </div>
      </form>

      {enrolling && (
        <div>
          <p>{status}</p>
          <FaceAuth mode="enroll" label={name || email} onEnrolled={handleEnrolled} />
        </div>
      )}

      {!enrolling && (
        <div>
          <p>After creating account, click Create & Enroll Face to capture your face.</p>
        </div>
      )}
    </div>
  );
}
