from controllers.ollamaController import ollama_bp
from controllers.userController import user_bp
from controllers.lessonsController import lessons_bp
from flask import Flask
from data.dataConfig import init_db
from data.firebaseConfig import init_firebase


app = Flask(__name__)

init_db(app)
init_firebase()

app.register_blueprint(user_bp, url_prefix="/users")

app.register_blueprint(ollama_bp, url_prefix="/ollama")

app.register_blueprint(lessons_bp, url_prefix="/lessons")

if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=5050)
