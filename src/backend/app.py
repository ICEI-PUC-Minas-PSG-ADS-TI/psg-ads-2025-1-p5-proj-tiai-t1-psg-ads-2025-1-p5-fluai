from controllers.ollamaController import ollama_bp
from controllers.userController import user_bp
from controllers.lessonsController import lessons_bp
from flask import Flask
from data.dataConfig import init_db
from data.firebaseConfig import init_firebase


def create_app():
    app = Flask(__name__)

    app.register_blueprint(user_bp, url_prefix="/users")

    app.register_blueprint(ollama_bp, url_prefix="/ollama")

    app.register_blueprint(lessons_bp, url_prefix="/lessons")

    init_db(app)

    return app


init_firebase()
