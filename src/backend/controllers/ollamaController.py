from flask import Blueprint, request, jsonify
import re
from services.userService import get_user_by_email, update_user_english_level
from services.ollamaService import generate_text_from_ollama, save_lessons, save_test_lessons
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
                save_test_lessons(question, answer, "leveling test", options)
            except KeyError:
                continue

        if response:
            return jsonify({"response": "Teste de nivelamento gerado com sucesso."}), 200
        else:
            return jsonify({"error": "Erro ao gerar teste com a IA."}), 500
    except json.JSONDecodeError:
        return jsonify({"error": "Resposta inválida da IA."}), 500


@ollama_bp.route("/define-user-english-level", methods=["POST"])
def define_user_english_level():
    try:
        data = request.get_json()
        email = data.get("email")
        questions = data.get("questions")
        prompt = f"Você é um avaliador de proficiência em inglês. Baseado nas respostas do usuário nas perguntas abaixo, classifique diretamente o nível de inglês do usuário segundo o Quadro Europeu Comum de Referência para Línguas (A1, A2, B1, B2, C1 ou C2). Apenas retorne o nível, sem qualquer explicação, comentário ou texto adicional. Perguntas e respostas do usuário: {questions}. Responda apenas com apenas UM dos seguintes níveis: A1, A2, B1, B2, C1 ou C2"

        response = generate_text_from_ollama(prompt)

        print(response)

        if not response:
            return jsonify({"error": f"Erro ao definir nível de inglês com a IA.{response}"}), 400

        user = update_user_english_level(response, email, "")

        if not user:
            return jsonify({"error": "Erro ao definir nível de inglês."}), 400

        return jsonify({"response": f"Nível de inglês definido como {user.level}."}), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500


@ollama_bp.route("/generate-custom-activity", methods=["POST"])
def generate_custom_activity(email):
    user = get_user_by_email(email)
    if not user:
        print(f"Usuário não encontrado para email {email}")
        return

    user_level = user.level or "A1"
    progress = user.progress_history or "sem histórico registrado"

    prompt = (
        f"Crie um array JSON com exatamente 5 objetos. Cada objeto representa uma questão de múltipla escolha de preenchimento de lacunas, adequada ao nível {user_level}, com base nos tópicos: {progress}. "
        "Cada objeto deve conter: "
        "\"question\": uma frase com lacuna, "
        "\"options\": um array com 4 alternativas no formato ['a) ...', 'b) ...', 'c) ...', 'd) ...'], "
        "\"answer\": a alternativa correta exatamente como em options, como 'b) take'. "
        "Os temas devem incluir verb patterns, collocations, phrasal verbs, prepositions e modal verbs. "
        "Responda com um único array JSON válido e compacto, sem explicações, sem marcações como <think>, sem comentários, sem formatação Markdown ou código. Apenas o JSON puro, em uma única linha."
    )

    response = generate_text_from_ollama(prompt)

    match = re.search(r"\[.*\]", response, re.DOTALL)

    if not match:
        print("Resposta da IA não contém JSON válido.")
        return

    json_text = match.group(0)

    try:
        questions = json.loads(json_text)
        for q in questions:
            question = q["question"]
            answer = q["answer"]
            options = q["options"]
            save_lessons(question, answer, "custom activity",
                         user_level, options)
    except Exception as e:
        print(f"Erro ao salvar questões: {str(e)}")


@ollama_bp.route("/fix-user-responses", methods=["GET"])
def fix_user_responses():

    data = request.get_json()
    email = data.get("email")
    questions = data.get("questions")

    user = get_user_by_email(email)
    if not user:
        return jsonify({"error": "Usuário não encontrado"}), 404

    description = user.progress_history or "usuário não possuí descrição"

    prompt = f"""
            Você é um avaliador especialista em proficiência em inglês segundo o Quadro Europeu Comum de Referência para Línguas (CEFR).
            Com base nas perguntas e respostas fornecidas abaixo, avalie o nível de inglês do usuário e atualize sua descrição de aprendizado.
            Descrição anterior: "{description}"
            Perguntas e respostas do usuário:
            {questions}
            Instruções importantes:
            Responda apenas com um JSON válido.
            O JSON deve conter:
            "level": com um dos valores exatos entre: "A1", "A2", "B1", "B2", "C1", "C2"
            "description": uma frase curta (1 a 2 frases) descrevendo o nível atual do usuário
            Exemplo de resposta:
            {{ "level": "B1", "description": "O usuário compreende textos simples e consegue manter conversas básicas sobre temas familiares." }}
            Não inclua comentários, explicações ou outro texto fora do JSON. """

    response = generate_text_from_ollama(prompt)

    if not response:
        return jsonify({"error": "Erro ao definir nível de inglês com a IA."}), 400

    try:
        response_json = json.loads(response.strip())
        level = response_json.get("level")
        description = response_json.get("description")

        print(f"Resposta da IA: {level}")
        print(f"Resposta da IA: {description}")

        if not level or not description:
            return jsonify({"error": "Campos esperados não encontrados."}), 404

        user = update_user_english_level(level, email, description)

        if not user:
            return jsonify({"error": "Erro ao definir nível de inglês."}), 400

        return jsonify({
            "response": f"Nível de inglês definido como {user.level}.",
            "level": level,
            "description": description
        }), 200

    except json.JSONDecodeError:
        return jsonify({
            "error": "Resposta da IA em formato inesperado",
            "raw": response
        }), 400
