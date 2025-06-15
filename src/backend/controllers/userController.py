from flask import request, jsonify, Blueprint
from services.userService import create_user_firebase, create_user_postegre, get_user_by_email, recover_password_firebase, update_user_password


user_bp = Blueprint("user", __name__)


@user_bp.route("/signup", methods=["POST"])
def signup():
    data = request.get_json()
    username = data.get("username")
    password = data.get("password")
    email = data.get("email")

    if len(password) < 6:
        return jsonify({"error": "A senha precisa ter no mínimo 6 caracteres."}), 400

    if username and password and email:
        user = get_user_by_email(email)
        if user:
            return jsonify({"error": "Esse e-mail já existe."}), 400

        userFirebase = create_user_firebase(email, password)
        userPostegre = create_user_postegre(username, email)

        if userFirebase and userPostegre:
            return jsonify({"message": "Usuário criado com sucesso."}), 201

    return jsonify({"error": "Todos os campos são obrigatórios."}), 400


@user_bp.route("/recover-password", methods=["POST"])
def recover_password():
    try:
        data = request.get_json()
        email = data.get("email")

        if not email:
            return jsonify({"error": "Todos os campos são obrigatórios."}), 400

        user = get_user_by_email(email)
        if not user:
            return jsonify({"error": "Usuário não encontrado."}), 404

        link = recover_password_firebase(email)

        if not link:
            return jsonify({"error": "Erro ao enviar o link de recuperação de senha."}), 500

        return jsonify({"message": link}), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500


@user_bp.route("/verify-leveling-test", methods=["POST"])
def verify_leveling_test():
    try:
        data = request.get_json()
        email = data.get("email")

        user = get_user_by_email(email)

        if not user:
            return jsonify({"error": "Usuário não encontrado."}), 404

        if not user.level:
            return False, 404

        return True, 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500
