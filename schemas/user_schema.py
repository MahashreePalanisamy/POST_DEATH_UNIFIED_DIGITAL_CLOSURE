from pydantic import BaseModel, EmailStr


# Used for registering user
class UserCreate(BaseModel):
    name: str
    email: EmailStr
    password: str


# Used for returning user data (response model)
class UserResponse(BaseModel):
    id: int
    name: str
    email: EmailStr

    class Config:
        from_attributes = True   # for SQLAlchemy compatibility (Python 3.12+)