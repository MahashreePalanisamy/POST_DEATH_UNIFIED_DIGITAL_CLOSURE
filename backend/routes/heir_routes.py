from fastapi import APIRouter, UploadFile, File, Form, Depends
from sqlalchemy.orm import Session
from models.database import SessionLocal
from models.heir_model import Heir
import shutil
import os

router = APIRouter(prefix="/heirs", tags=["Heirs"])

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


@router.post("/add")
def add_heir(
    user_id: int = Form(...),
    name: str = Form(...),
    email: str = Form(...),
    phone: str = Form(...),
    relationship_type: str = Form(...),
    aadhaar: str = Form(...),
    id_proof: UploadFile = File(...),
    db: Session = Depends(get_db)
):

    os.makedirs("uploads/heir_docs", exist_ok=True)

    doc_path = f"uploads/heir_docs/{id_proof.filename}"

    with open(doc_path, "wb") as buffer:
        shutil.copyfileobj(id_proof.file, buffer)

    heir = Heir(
        user_id=user_id,
        name=name,
        email=email,
        phone=phone,
        relationship_type=relationship_type,
        aadhaar=aadhaar,
        id_proof_path=doc_path
    )

    db.add(heir)
    db.commit()

    return {"message": "Heir added successfully"}