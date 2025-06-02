from flask import Blueprint, request, jsonify
from services.userService import update_user_english_level
from services.ollamaService import generate_text_from_ollama, save_lessons, clean_and_parse_json
import json

ollama_bp = Blueprint("ollama", __name__)


@ollama_bp.route("/generate", methods=["POST"])
def generate_text():
    data = request.get_json()
    prompt = data.get("prompt")

    if not prompt:
        return jsonify({"error": "O prompt é obrigatório."}), 400

    response = generate_text_from_ollama(prompt)

    if response:
        return jsonify({"response": response})
    else:
        return jsonify({"error": "Erro ao gerar o texto."}), 500


@ollama_bp.route("/generate-level-test", methods=["GET"])
def generate_level_test():
    prompt = 'Gere um array JSON com exatamente 10 objetos, cada um representando uma questão de múltipla escolha no formato de preenchimento de lacunas, como no exemplo: {"question": "Would you mind ...... these plates a wipe before putting them in the cupboard?", "options": ["a) making", "b) doing", "c) getting", "d) giving"], "answer": "d) giving"}. Todas as questões devem conter lacunas (gap fill) com apenas uma resposta correta entre quatro alternativas no formato "a) opção", "b) opção", etc. Os temas devem incluir verb patterns, collocations, phrasal verbs, prepositions e modal verbs. Inclua exatamente 3 questões fáceis, 4 médias e 3 difíceis. Retorne apenas o array JSON puro, sem explicações, quebras de linha extras ou texto adicional.'

    response = generate_text_from_ollama(prompt)
    try:
        data = json.loads(response)
        for q in data:
            try:
                question = q["question"]
                answer = q["answer"]
                options = q["options"]
                save_lessons(question, answer, "leveling test", options)
            except KeyError:
                continue

        if response:
            return jsonify({"response": "Teste de nivelamento gerado com sucesso."}), 200
        else:
            return jsonify({"error": "Erro ao gerar teste com a IA."}), 500
    except json.JSONDecodeError:
        return jsonify({"error": "Resposta inválida da IA."}), 500


@ollama_bp.route("/define-user-english-level", methods=["GET"])
def define_user_english_level():
    try:
        data = request.get_json()
        user_id = data.get("user_id")
        questions = data.get("questions")
        prompt = f"Você é um avaliador de proficiência em inglês. Baseado nas respostas do usuário nas perguntas abaixo, classifique diretamente o nível de inglês do usuário segundo o Quadro Europeu Comum de Referência para Línguas (A1, A2, B1, B2, C1 ou C2). Apenas retorne o nível, sem qualquer explicação, comentário ou texto adicional. Perguntas e respostas do usuário: {questions}. Responda apenas com apenas UM dos seguintes níveis: A1, A2, B1, B2, C1 ou C2"

        response = generate_text_from_ollama(prompt)

        if not response:
            return jsonify({"error": "Erro ao definir nível de inglês com a IA."}), 400

        user = update_user_english_level(response, user_id)

        if not user:
            return jsonify({"error": "Erro ao definir nível de inglês."}), 400

        return jsonify({"response": f"Nível de inglês definido como {user.level}."}), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500


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
