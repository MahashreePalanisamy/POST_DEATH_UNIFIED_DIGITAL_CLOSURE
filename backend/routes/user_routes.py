from fastapi import APIRouter, Depends, Form, File, UploadFile, HTTPException
from sqlalchemy.orm import Session
from backend.models.database import get_db
from backend.models.user_model import User
from utils.auth_utils import hash_password, verify_password, create_access_token
from utils.security_utils import hash_aadhaar
import shutil
import os

router = APIRouter()

# Ensure upload folders exist
os.makedirs("uploads/photos", exist_ok=True)
os.makedirs("uploads/videos", exist_ok=True)


# 🔐 Secure Register Route
@router.post("/register")
def register_user(
    name: str = Form(...),
    email: str = Form(...),
    password: str = Form(...),
    aadhaar: str = Form(...),
    photo: UploadFile = File(...),
    video: UploadFile = File(...),
    db: Session = Depends(get_db)
):

    # Check if email already exists
    existing_user = db.query(User).filter(User.email == email).first()
    if existing_user:
        raise HTTPException(status_code=400, detail="Email already registered")

    # 🔐 Hash password
    hashed_password = hash_password(password)

    # 🔐 Hash Aadhaar
    aadhaar_hash = hash_aadhaar(aadhaar)

    # Save uploaded files
    photo_path = f"uploads/photos/{photo.filename}"
    video_path = f"uploads/videos/{video.filename}"

    with open(photo_path, "wb") as buffer:
        shutil.copyfileobj(photo.file, buffer)

    with open(video_path, "wb") as buffer:
        shutil.copyfileobj(video.file, buffer)

    # Create user object
    new_user = User(
        name=name,
        email=email,
        password=hashed_password,
        aadhaar_hash=aadhaar_hash,
        photo_path=photo_path,
        video_path=video_path
    )

    db.add(new_user)
    db.commit()
    db.refresh(new_user)

    return {"message": "User registered securely"}


# 🔐 Login Route with JWT
@router.post("/login")
def login(
    email: str = Form(...),
    password: str = Form(...),
    db: Session = Depends(get_db)
):

    # Check if user exists
    user = db.query(User).filter(User.email == email).first()
    if not user:
        raise HTTPException(status_code=401, detail="Invalid email")

    # Verify password
    if not verify_password(password, user.password):
        raise HTTPException(status_code=401, detail="Invalid password")

    # Create JWT access token
    access_token = create_access_token(data={"sub": user.email})

    return {
        "access_token": access_token,
        "token_type": "bearer"
    }