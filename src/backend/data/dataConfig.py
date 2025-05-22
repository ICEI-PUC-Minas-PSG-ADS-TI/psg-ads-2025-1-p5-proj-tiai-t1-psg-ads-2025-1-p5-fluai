from flask_sqlalchemy import SQLAlchemy
from flask import Flask

db = SQLAlchemy()


def init_db(app: Flask):
    # URL para o Docker
    app.config["SQLALCHEMY_DATABASE_URI"] = "postgresql://docker:docker@database:5432/fluaidb"
    # app.config["SQLALCHEMY_DATABASE_URI"] = "postgresql://docker:docker@localhost:5432/fluaidb" # URL para o localhost
    db.init_app(app)
