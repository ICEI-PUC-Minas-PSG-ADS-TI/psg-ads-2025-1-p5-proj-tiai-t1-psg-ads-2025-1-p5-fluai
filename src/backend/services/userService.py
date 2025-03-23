from data.dataConfig import db
from models.userModel import Users
from werkzeug.security import generate_password_hash, check_password_hash
from data.firebaseConfig import firebase_config
from firebase_admin import auth


def create_user_postegre(username, password, email):
    user = Users(username=username,
                 password=generate_password_hash(password), email=email)
    db.session.add(user)
    db.session.commit()
    return user


def get_user_by_email(email):
    return Users.query.filter_by(email=email).first()


def create_user_firebase(email, password):
    user = auth.create_user(email=email, password=password)
    return user
