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
    else:
        return jsonify({"error": "Erro ao gerar teste com a IA."}), 500
    
    
@ollama_bp.route("/evaluate-level-test", methods=["POST"])
def evaluate_level_test():
    data = request.get_json()
    answers = data.get("answers")

    if not answers:
        return jsonify({"error": "Respostas são obrigatórias."}), 400

    prompt = {
        "prompt": "Corrija essas respostas e forneça um score baseado na precisão: ",
        "answers": answers
    }
    
    response = generate_text_from_ollama(prompt)

    if not response:
        return jsonify({"error": "Erro ao corrigir teste com a IA."}), 500

    try:
        score = int(response.get("score", 0))
    except ValueError:
        return jsonify({"error": "Resposta inválida da IA."}), 500

    if score >= 90:
        level = "A+"
    elif score >= 80:
        level = "A"
    elif score >= 70:
        level = "A1"
    elif score >= 60:
        level = "A2"
    elif score >= 50:
        level = "B"
    elif score >= 40:
        level = "B1"
    elif score >= 30:
        level = "B2"
    elif score >= 20:
        level = "C1"
    else:
        level = "C2"

    return jsonify({"level": level})