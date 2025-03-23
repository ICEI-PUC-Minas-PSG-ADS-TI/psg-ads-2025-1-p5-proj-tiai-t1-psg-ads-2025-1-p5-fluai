from controllers.userController import user_bp
from flask import Flask
from data.dataConfig import init_db


app = Flask(__name__)

init_db(app)

# importando as controllers do blueeprint
app.register_blueprint(user_bp, url_prefix="/users")


if __name__ == "__main__":
    app.run(debug=True)
