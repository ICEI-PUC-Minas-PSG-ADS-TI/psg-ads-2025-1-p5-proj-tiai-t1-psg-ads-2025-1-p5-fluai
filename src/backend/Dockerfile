FROM python:3.11-slim

WORKDIR /app

COPY . /app

RUN pip install -r requirements.txt

EXPOSE 5050

CMD [ "python", "app.py", "5050" , "--host", "0.0.0.0"]