from fastapi import FastAPI

from backend.models.database import engine, Base
from backend.routes import user_routes, heir_routes, asset_routes

app = FastAPI()

Base.metadata.create_all(bind=engine)

app.include_router(user_routes.router)
app.include_router(heir_routes.router)
app.include_router(asset_routes.router)

@app.get("/")
def home():
    return {"message": "Post Death Digital Vault API Running"}