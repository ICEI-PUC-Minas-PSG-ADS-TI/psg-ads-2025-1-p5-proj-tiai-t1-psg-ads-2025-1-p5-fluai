from firebase_admin import auth
from flask import request


def verify_token(token):
    auth_header = request.headers.get("Authorization")

    if not auth_header:
        return None

    token = auth_header.split("Bearer ")[-1]

    try:
        decoded_token = auth.verify_id_token(token)
        return decoded_token["uid"]
    except:
        return None
