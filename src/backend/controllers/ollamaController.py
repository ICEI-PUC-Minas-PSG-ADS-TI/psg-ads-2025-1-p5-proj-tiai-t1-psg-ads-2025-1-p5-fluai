from flask import Blueprint, request, jsonify
from services.ollamaService import generate_text_from_ollama

ollama_bp = Blueprint("ollama", __name__)


@ollama_bp.route("/generate", methods=["POST"])
def generate_text():
    data = request.get_json()
    prompt = data.get("prompt")

    if not prompt:
        return jsonify({"error": "Prompt is required"}), 400

    response = generate_text_from_ollama(prompt)

    if response:
        return jsonify({"response": response})
    else:
        return jsonify({"error": "Failed to generate text"}), 500

@ollama_bp.route("/generate-level-test", methods=["GET"])
def generate_level_test():
    prompt = "Crie 10 questões de inglês com diferentes níveis de dificuldade (fácil, médio, difícil) e opções de resposta."
    
    response = generate_text_from_ollama(prompt)
    
    if response:
        return jsonify({"questions": response})