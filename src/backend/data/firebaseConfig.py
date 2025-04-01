import os
import firebase_admin
from firebase_admin import credentials

# Pegando o caminho absoluto para o repositório base, já que o programa vai rodar em linux e windows
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
SERVICE_ACCOUNT_PATH = os.path.join(BASE_DIR, "serviceAccountKey.json")


def init_firebase():
    cred = credentials.Certificate(SERVICE_ACCOUNT_PATH)
    firebase_admin.initialize_app(cred)
