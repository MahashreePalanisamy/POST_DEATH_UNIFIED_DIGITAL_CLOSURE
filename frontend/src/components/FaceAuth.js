import React, { useEffect, useRef, useState } from 'react';

export default function FaceAuth({ mode = 'verify', label, onEnrolled, onResult }) {
  const videoRef = useRef(null);
  const [loadingModels, setLoadingModels] = useState(true);
  const [running, setRunning] = useState(false);

  useEffect(() => {
    const load = async () => {
      setLoadingModels(true);

      const faceapi = window.faceapi;
      const MODEL_URL = '/models';

      try {
        await faceapi.nets.tinyFaceDetector.loadFromUri(MODEL_URL);
        await faceapi.nets.faceLandmark68Net.loadFromUri(MODEL_URL);
        await faceapi.nets.faceRecognitionNet.loadFromUri(MODEL_URL);
      } catch (err) {
        console.error('Error loading face-api models. Make sure /public/models exists', err);
      }

      setLoadingModels(false);
    };

    load();
  }, []);

  useEffect(() => {
    return () => {
      stopVideo();
    };
  }, []);

  const startVideo = async () => {
    try {
      const stream = await navigator.mediaDevices.getUserMedia({ video: true });
      videoRef.current.srcObject = stream;
      await videoRef.current.play();
      setRunning(true);
    } catch (err) {
      console.error('Could not start camera', err);
      alert('Could not access camera. Check permissions.');
    }
  };

  const stopVideo = () => {
    if (!videoRef.current) return;

    const stream = videoRef.current.srcObject;
    if (stream) {
      stream.getTracks().forEach(t => t.stop());
      videoRef.current.srcObject = null;
    }

    setRunning(false);
  };

  const captureDescriptor = async () => {
    try {
      const faceapi = window.faceapi;
      const options = new faceapi.TinyFaceDetectorOptions();

      const detection = await faceapi
        .detectSingleFace(videoRef.current, options)
        .withFaceLandmarks()
        .withFaceDescriptor();

      if (!detection) return null;

      const descriptor = detection.descriptor;
      return Array.from(descriptor);
    } catch (err) {
      console.error('Error capturing descriptor:', err);
      return null;
    }
  };

  const handleEnroll = async () => {
    if (!label) return alert('Missing label for enrollment');
    if (loadingModels) return alert('Models still loading');

    await startVideo();

    setTimeout(async () => {
      const desc = await captureDescriptor();

      if (!desc) {
        alert('No face detected. Try again.');
        stopVideo();
        return;
      }

      stopVideo();
      if (onEnrolled) onEnrolled(label, desc);
    }, 1200);
  };

  const handleVerify = async () => {
    if (loadingModels) return alert('Models still loading');

    await startVideo();

    setTimeout(async () => {
      try {
        const desc = await captureDescriptor();
        stopVideo();

        if (!desc) {
          if (onResult) onResult({ matchLabel: null, distance: null, ok: false });
          return;
        }

        let profiles = [];
        const stored = localStorage.getItem('face_profiles');
        profiles = stored ? JSON.parse(stored) : [];

        if (!profiles.length) {
          if (onResult) onResult({ matchLabel: null, distance: null, ok: false });
          return;
        }

        let best = { label: null, distance: Infinity };

        for (const p of profiles) {
          if (!p.descriptor || !Array.isArray(p.descriptor)) continue;

          const d = euclideanDistance(p.descriptor, desc);
          if (d < best.distance) {
            best = { label: p.label, distance: d };
          }
        }

        const threshold = 0.6;
        const matched = best.distance <= threshold;

        if (onResult)
          onResult({
            matchLabel: matched ? best.label : null,
            distance: best.distance,
            ok: matched,
          });

      } catch (err) {
        console.error('Error in handleVerify:', err);
        stopVideo();
        if (onResult) onResult({ matchLabel: null, distance: null, ok: false });
      }
    }, 1000);
  };

  const euclideanDistance = (a, b) => {
    let sum = 0;
    for (let i = 0; i < a.length; i++) {
      const diff = (a[i] || 0) - (b[i] || 0);
      sum += diff * diff;
    }
    return Math.sqrt(sum);
  };

  return (
    <div>
      <div style={{ marginBottom: 8 }}>
        <video
          ref={videoRef}
          width={320}
          height={240}
          style={{ border: '1px solid #ccc' }}
        />
      </div>

      {loadingModels ? (
        <div>Loading face models...</div>
      ) : (
        <div>
          {mode === 'enroll' ? (
            <button onClick={handleEnroll}>
              Start Enrollment (capture face)
            </button>
          ) : (
            <button onClick={handleVerify}>
              Start Verification (scan face)
            </button>
          )}

          {running && (
            <button onClick={stopVideo} style={{ marginLeft: 8 }}>
              Stop
            </button>
          )}
        </div>
      )}
    </div>
  );
}
