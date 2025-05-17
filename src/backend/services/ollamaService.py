import requests
from data.dataConfig import db
from models.lessonModel import Lesson
import json

OLLAMA_URL = "http://ollama:11434/api/generate"


def generate_text_from_ollama(prompt):
    data = {
        "model": "llama3",
        "prompt": prompt,
        "stream": False,
    }

    headers = {
        "Content-Type": "application/json"
    }

    response = requests.post(OLLAMA_URL, json=data, headers=headers)

    if response.status_code == 200:
        return response.json()["response"]
    else:
        return None

def save_lessons(question, answer, description, options):
    lesson = Lesson(
    question=question,
    answer=answer,
    description=description,
    options=json.dumps(options) 
)

    db.session.add(lesson)
    db.session.commit()
    return lesson
