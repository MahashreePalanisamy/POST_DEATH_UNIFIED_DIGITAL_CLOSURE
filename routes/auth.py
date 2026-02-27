from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from database import SessionLocal
from schemas.user_schema import UserCreate
from models.user import User
from utils.security import hash_password

router = APIRouter()   # ✅ THIS WAS MISSING


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


@router.post("/register")
def register(user: UserCreate, db: Session = Depends(get_db)):
    hashed_pwd = hash_password(user.password)
    new_user = User(email=user.email, password=hashed_pwd)

    db.add(new_user)
    db.commit()
    db.refresh(new_user)

    return {"message": "User registered successfully"}