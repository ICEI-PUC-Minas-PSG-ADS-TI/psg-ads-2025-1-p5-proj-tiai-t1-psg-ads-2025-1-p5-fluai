from controllers.userController import user_bp
from flask import Flask
from data.dataConfig import init_db
from data.firebaseConfig import init_firebase


app = Flask(__name__)

init_db(app)
init_firebase()

app.register_blueprint(user_bp, url_prefix="/users")


if __name__ == "__main__":
    app.run(debug=True)
