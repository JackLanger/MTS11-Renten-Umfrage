import json
import os
import sys
from os import path

from ExcelWriter import ExcelWriter

from DataHandler import DataHandler
from SQLQueryHandler import SQLQueryHandler
from dotenv import load_dotenv

load_dotenv()
SERVER = os.getenv("SERVER")
USER = os.getenv("USER")
PASSWORD = os.getenv("PASSWORD")
DATABASE = os.getenv("DATABASE")

UMFRAGE_TABLES = {
    "question": "t_question",
    "question_answer": "t_question_answer",
    "user_answer": "t_user_answer"
}


def replace_session_id(data):
    counter = 0
    new_data = {}
    for k in data.keys():
        new_data[f"user_{counter}"] = data[k]
        counter += 1
    del data
    return new_data


def main(filepath,file_name="Data"):
    sql_query_handler = SQLQueryHandler(SERVER, USER, PASSWORD, DATABASE)
    xlsx_path = path.join(filepath, f"{file_name}.xlsx")
    excel_writer = ExcelWriter(xlsx_path)
    data_handler = DataHandler(sql_query_handler)
    user_answer_dict = data_handler.create_user_answer_dict()
    question_answer_dict = data_handler.create_question_answer_dict()
    cover_dict = data_handler.create_cover_dict(question_answer_dict)
    for k, v in user_answer_dict.items():
        v = dict(sorted(v.items()))
        user_answer_dict[k] = v

    user_answer_dict = replace_session_id(user_answer_dict)
    response_dict = {
        "question_answer": question_answer_dict,
        "answers": user_answer_dict
    }
    json_path = path.join(filepath, f"{file_name}.json")
    with open(json_path, "w", encoding="utf-8") as f:
        f.write(json.dumps(response_dict, indent=4, ensure_ascii=False))
    excel_writer.create_cover(cover_dict)
    excel_writer.create_data(user_answer_dict, question_answer_dict)
    excel_writer.save()


if __name__ == '__main__':
    PATH = None
    PATH = sys.argv[1] if len(sys.argv)>1 else path.dirname(path.abspath(__file__))
    filename = sys.argv[2] if len(sys.argv) > 2 else "Data"
    main(PATH, filename)
