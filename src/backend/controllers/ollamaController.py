from flask import Blueprint, request, jsonify
from services.ollamaService import generate_text_from_ollama, save_lessons
import json

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
    prompt = "Gere um JSON contendo exatamente 10 objetos, cada um representando uma questão de múltipla escolha para um teste de nivelamento de inglês. O JSON deve ser uma lista com elementos no seguinte formato: {'question': 'Would you mind ...... these plates a wipe before putting them in the cupboard?', 'options': ['a) making', 'b) doing', 'c) getting', 'd) giving'], 'answer': 'd) giving'}. As 10 questões devem ser divididas em níveis: 3 fáceis, 4 médias e 3 difíceis. Todas devem focar em gramática prática e uso natural da língua, como: verb patterns, collocations, phrasal verbs, prepositions e modals. Use sempre lacunas nas frases e 4 alternativas. Não adicione nenhum texto fora do JSON. Apenas retorne o array JSON puro."
    
    
    response = generate_text_from_ollama(prompt)
    data = json.loads(response)
    for q in data:
        question = q["question"]
        answer = q["answer"]
        options = q["options"]        
        save_lessons(question, answer,"leveling test", options)  
    

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