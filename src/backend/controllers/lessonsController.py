from flask import Blueprint, request, jsonify
from services.lessonsService import get_leveling_tests


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
