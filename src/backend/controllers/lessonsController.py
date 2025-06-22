from flask import Blueprint, request, jsonify, current_app
from services.lessonsService import get_leveling_tests, get_custom_activity
from controllers.ollamaController import generate_custom_activity
from threading import Thread
import time


lessons_bp = Blueprint('lessons', __name__)

cooldowns = {}
COOLDOWN_SECONDS = 50


@lessons_bp.route('/leveling-tests', methods=['GET'])
def get_leveling_tests_route():
    try:
        leveling_tests = get_leveling_tests()

        if not leveling_tests:
            return jsonify({"error": "Nenhum teste de nivelamento encontrado"}), 404

        return jsonify([lesson.to_dict() for lesson in leveling_tests])

    except Exception as e:
        return jsonify({"error": str(e)}), 500


@lessons_bp.route('/custom-activity', methods=['POST'])
def get_custom_activity_route():
    try:
        data = request.get_json()
        email = data.get('email')

        if not email:
            return jsonify({"error": "O email é obrigatório"}), 400

        now = time.time()
        last_access = cooldowns.get(email)

        if last_access and now - last_access < COOLDOWN_SECONDS:
            remaining = int(COOLDOWN_SECONDS - (now - last_access))
            return jsonify({"error": f"Suas atividades estão sendo geradas."}), 429

        cooldowns[email] = now

        custom_activity = get_custom_activity()

        app = current_app._get_current_object()

        def background_task(email, app=app):
            with app.app_context():
                generate_custom_activity(email)

        Thread(target=background_task, args=(email, app)).start()

        if not custom_activity:
            return jsonify({"error": "Nenhuma atividade personalizada encontrada"}), 404

        return jsonify([lesson.to_dict() for lesson in custom_activity])

    except Exception as e:
        return jsonify({"error": str(e)}), 500
