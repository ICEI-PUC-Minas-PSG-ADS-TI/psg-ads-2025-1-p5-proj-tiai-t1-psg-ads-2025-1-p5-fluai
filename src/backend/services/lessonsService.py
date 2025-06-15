from data.dataConfig import db
from models.lessonModel import Lesson


def get_leveling_tests():
    lessons = Lesson.query.filter_by(
        description="leveling test").limit(10).all()
    return lessons


def get_custom_activity():
    lessons = Lesson.query.filter_by(
        description="custom activity").limit(5).all()
    return lessons
