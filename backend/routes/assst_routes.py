from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from models.database import SessionLocal
from models.asset_model import DigitalAsset

router = APIRouter(prefix="/assets", tags=["Assets"])

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


@router.post("/add")
def add_asset(
    user_id: int,
    platform_name: str,
    account_username: str,
    account_email: str,
    account_password: str,
    db: Session = Depends(get_db)
):

    asset = DigitalAsset(
        user_id=user_id,
        platform_name=platform_name,
        account_username=account_username,
        account_email=account_email,
        account_password=account_password
    )

    db.add(asset)
    db.commit()

    return {"message": "Asset added successfully"}