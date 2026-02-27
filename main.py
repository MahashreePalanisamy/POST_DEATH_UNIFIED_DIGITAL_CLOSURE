from fastapi import FastAPI
from database import engine
from models import user
from routes import auth

app = FastAPI()

user.Base.metadata.create_all(bind=engine)

app.include_router(auth.router)