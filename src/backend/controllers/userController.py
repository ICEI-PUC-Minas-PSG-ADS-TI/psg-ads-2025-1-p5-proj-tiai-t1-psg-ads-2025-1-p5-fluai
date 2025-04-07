from flask import request, jsonify, Blueprint
from services.userService import create_user_firebase, create_user_postegre, get_user_by_email


user_bp = Blueprint("user", __name__)


@user_bp.route("/signup", methods=["POST"])
def signup():
    data = request.get_json()
    username = data.get("username")
    password = data.get("password")
    email = data.get("email")

    if len(password) < 6:
        return jsonify({"error": "Password must be at least 6 characters"}), 400

    if username and password and email:
        user = get_user_by_email(email)
        if user:
            return jsonify({"error": "Email already exists"}), 400

        userFirebase = create_user_firebase(email, password)
        userPostegre = create_user_postegre(username, password, email)

        if userFirebase and userPostegre:
            return jsonify({"message": "User created successfully"}), 201

    return jsonify({"error": "Invalid data"}), 400


user_bp.route("/login", methods=["POST"])
