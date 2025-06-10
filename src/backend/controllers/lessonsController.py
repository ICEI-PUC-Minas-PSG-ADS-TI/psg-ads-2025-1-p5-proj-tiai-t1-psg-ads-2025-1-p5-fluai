from flask import Blueprint, request, jsonify
from services.lessonsService import get_leveling_tests, get_custom_activity


lessons_bp = Blueprint('lessons', __name__)


@lessons_bp.route('/leveling-tests', methods=['GET'])
def get_leveling_tests_route():
    try:
        leveling_tests = get_leveling_tests()

        if not leveling_tests:
            return jsonify({"error": "Nenhum teste de nivelamento encontrado"}), 404

        return jsonify([lesson.to_dict() for lesson in leveling_tests])

    except Exception as e:
        return jsonify({"error": str(e)}), 500


@lessons_bp.route('/custom-activity', methods=['GET'])
def get_custom_activity_route():
    try:
        custom_activity = get_custom_activity()

        if not custom_activity:
            return jsonify({"error": "Nenhuma atividade personalizada encontrada"}), 404

        return jsonify([lesson.to_dict() for lesson in custom_activity])

    except Exception as e:
        return jsonify({"error": str(e)}), 500
