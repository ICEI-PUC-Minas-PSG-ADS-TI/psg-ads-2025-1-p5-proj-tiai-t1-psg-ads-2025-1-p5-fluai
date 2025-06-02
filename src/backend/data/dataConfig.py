from flask_sqlalchemy import SQLAlchemy
from flask import Flask

db = SQLAlchemy()


def init_db(app: Flask):
    # app.config["SQLALCHEMY_DATABASE_URI"] = "postgresql://docker:docker@database:5432/fluaidb" # URL para o Docker
    # URL para o localhost
    app.config["SQLALCHEMY_DATABASE_URI"] = "postgresql://docker:docker@localhost:5432/fluaidb"
    db.init_app(app)
