from data.dataConfig import db
from sqlalchemy.sql import func


class Lesson(db.Model):
    __tablename__ = "lesson"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    question = db.Column(db.String(300), nullable=False)
    answer = db.Column(db.String(200), nullable=False)
    description = db.Column(db.String(100), nullable=False)
    level = db.Column(db.String(2), nullable=True, default="")
    options = db.Column(db.String(200), nullable=False)

    def __repr__(self):
        return f"<Lesson {self.id}: {self.question[:30]}...>"

    def to_dict(self):
        return {
            "id": self.id,
            "question": self.question,
            "answer": self.answer,
            "description": self.description,
            "level": self.level,
            "options": self.options
        }
