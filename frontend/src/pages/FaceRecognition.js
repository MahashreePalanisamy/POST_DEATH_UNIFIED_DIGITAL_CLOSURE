import React, { useEffect, useRef } from "react";

const FaceRecognition = () => {
  const videoRef = useRef();
  const canvasRef = useRef();

  useEffect(() => {
    const loadModels = async () => {
      const faceapi = window.faceapi;

      await faceapi.nets.tinyFaceDetector.loadFromUri("/models");
      startVideo();
    };

    loadModels();
  }, []);

  const startVideo = () => {
    navigator.mediaDevices
      .getUserMedia({ video: true })
      .then((stream) => {
        videoRef.current.srcObject = stream;
      })
      .catch((err) => console.error(err));
  };

  const handleVideoOnPlay = () => {
    const faceapi = window.faceapi;

    setInterval(async () => {
      const detections = await faceapi.detectAllFaces(
        videoRef.current,
        new faceapi.TinyFaceDetectorOptions()
      );

      const canvas = canvasRef.current;

      faceapi.matchDimensions(canvas, {
        width: videoRef.current.videoWidth,
        height: videoRef.current.videoHeight,
      });

      const resized = faceapi.resizeResults(detections, {
        width: videoRef.current.videoWidth,
        height: videoRef.current.videoHeight,
      });

      canvas.getContext("2d").clearRect(
        0,
        0,
        canvas.width,
        canvas.height
      );

      faceapi.draw.drawDetections(canvas, resized);
    }, 100);
  };

  return (
    <div style={{ textAlign: "center", marginTop: 40 }}>
      <h2>Face Detection</h2>
      <div style={{ position: "relative", display: "inline-block" }}>
        <video
          ref={videoRef}
          autoPlay
          muted
          width="600"
          height="450"
          onPlay={handleVideoOnPlay}
          style={{ position: "absolute" }}
        />
        <canvas
          ref={canvasRef}
          width="600"
          height="450"
          style={{ position: "absolute" }}
        />
      </div>
    </div>
  );
};

export default FaceRecognition;