from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from models.database import Base

class Heir(Base):
    __tablename__ = "heirs"

    id = Column(Integer, primary_key=True)
    name = Column(String)
    email = Column(String)
    phone = Column(String)
    relationship_type = Column(String)
    aadhaar = Column(String)
    id_proof_path = Column(String)
    death_certificate_path = Column(String)

    user_id = Column(Integer, ForeignKey("users.id"))
    user = relationship("User", back_populates="heirs")