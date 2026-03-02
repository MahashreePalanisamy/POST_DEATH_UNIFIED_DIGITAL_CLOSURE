from sqlalchemy import Column, Integer, String, Boolean
from sqlalchemy.orm import relationship
from backend.models.database import Base

class User(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True)
    name = Column(String)
    email = Column(String, unique=True)
    password = Column(String)

    aadhaar_hash = Column(String)  # ZKP style
    role = Column(String, default="user")  # user / admin
    is_deceased = Column(Boolean, default=False)

    photo_path = Column(String)
    video_path = Column(String)

    heirs = relationship("Heir", back_populates="user")
    assets = relationship("DigitalAsset", back_populates="user")