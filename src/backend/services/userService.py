from data.dataConfig import db
from models.userModel import Users
from werkzeug.security import generate_password_hash
from firebase_admin import auth


def create_user_postegre(username, email):
    user = Users(username=username, email=email)
    db.session.add(user)
    db.session.commit()
    return user


def update_user_password(user_id, new_password):
    user = Users.query.get(user_id)
    if user:
        user.password = generate_password_hash(new_password)
        db.session.commit()
        return user


def get_user_by_email(email):
    return Users.query.filter_by(email=email).first()


def create_user_firebase(email, password):
    user = auth.create_user(email=email, password=password)
    return user


def recover_password_firebase(email):
    link = auth.generate_password_reset_link(email)
    return link


def update_user_english_level(english_level, user_id):
    user = Users.query.get(user_id)
    if user:
        user.level = english_level
        db.session.commit()
        return user
    return None
