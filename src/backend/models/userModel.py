from data.dataConfig import db
from sqlalchemy.sql import func


class Users(db.Model):
    __tablename__ = "users"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    username = db.Column(db.String(100), nullable=False)
    password = db.Column(db.String(200), nullable=False)
    email = db.Column(db.String(100), unique=True, nullable=False)
    created_at = db.Column(db.DateTime(timezone=True),
                           default=func.current_date())
    level = db.Column(db.String(3), default="A1")
    progress_history = db.Column(db.Text, default="")

    def __repr__(self):
        return f"<User: {self.username} {self.id}>"
