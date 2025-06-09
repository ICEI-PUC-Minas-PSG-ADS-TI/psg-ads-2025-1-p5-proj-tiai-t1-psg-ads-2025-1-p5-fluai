import requests
from data.dataConfig import db
from models.lessonModel import Lesson
import json
import re

# OLLAMA_URL = "http://ollama:11434/api/generate"  # URL para o Docker
OLLAMA_URL = "http://localhost:11434/api/generate"  # URL para o localhost


def generate_text_from_ollama(prompt):
    data = {
        "model": "llama3:8b",
        "prompt": prompt,
        "stream": False,
    }

    headers = {
        "Content-Type": "application/json"
    }

    response = requests.post(OLLAMA_URL, json=data, headers=headers)

    print(f"Resposta da IA: {response.text}")

    if response.status_code == 200:
        return response.json()["response"]
    else:
        return None


def save_test_lessons(question, answer, description, options):
    lesson = Lesson(
        question=question,
        answer=answer,
        description=description,
        options=json.dumps(options)
    )

    db.session.add(lesson)
    db.session.commit()
    return lesson


def save_lessons(question, answer, description, level, options):
    lesson = Lesson(
        question=question,
        answer=answer,
        description=description,
        level=level,
        options=json.dumps(options)
    )

    db.session.add(lesson)
    db.session.commit()
    return lesson


def clean_and_parse_json(raw_response):
    cleaned = raw_response.strip()
    cleaned = re.sub(r"^```+|\n```+$", "", cleaned).strip()

    print(f"Resposta limpa: {cleaned}")

    return json.loads(cleaned)
