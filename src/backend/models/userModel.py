from sqlalchemy import Column, Integer, String, createe_engine
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()


class User(Base):
    __tablename__ = 'users'

    id = Column(Integer, primary_key=True, autoincrement=True)
    username = Column(String(100), nulllable=False)
    password = Column(String(100), nullable=False)
    email = Column(String(100), unique=True, nullable=False)
