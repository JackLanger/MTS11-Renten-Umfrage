class DataHandler:
    def __init__(self, sql_query_handler, start_index=0, number_of_entries=None):
        self.sql_query_handler = sql_query_handler
        self.start_index = start_index
        self.number_of_entries = number_of_entries

    def create_user_answer_dict(self):
        query_set = self.sql_query_handler.query(
            f"SELECT t_user_answer.user_session_id, tqa.answer_option, tq.id  FROM t_user_answer INNER JOIN t_question_answer as tqa ON t_user_answer.question_answer_id = tqa.id INNER JOIN t_question as tq ON tqa.question_p_question_id = tq.id")
        user_answer_dict = {}
        for result in query_set:
            if result[0] not in user_answer_dict:
                user_answer_dict[result[0]] = {result[2]: [result[1]]}
            else:
                if result[2] not in user_answer_dict[result[0]]:
                    user_answer_dict[result[0]][result[2]] = [result[1]]
                else:
                    user_answer_dict[result[0]][result[2]].append(result[1])

        return user_answer_dict

    def create_question_answer_dict(self):
        query_set = self.sql_query_handler.query(
            f"SELECT tqa.answer_option,tq.question_text,tq.id FROM t_question_answer as tqa INNER JOIN t_question as tq ON tqa.question_p_question_id = tq.id")

        question_answer_dict = {}
        for result in query_set:
            answer_option = result[0]
            question_text = result[1]
            question_id = result[2]
            if question_id not in question_answer_dict.keys():
                question_answer_dict[question_id] = {
                    "question_text": question_text,
                    "answer_options": [answer_option]
                }
            else:
                question_answer_dict[question_id]["answer_options"].append(answer_option)
        return question_answer_dict

    def create_cover_dict(self, question_dict=None):
        if question_dict is None:
            question_dict = self.create_question_answer_dict()
        question_query = self.sql_query_handler.query("SELECT question_text,question_type FROM t_question")

        question_information = {}
        for question_text, question_type in question_query:
            question_information[question_text] = {"question_type": question_type}

        cover_dict = {}
        for k, v in question_dict.items():
            cover_dict[v["question_text"]] = {"answer_options": v["answer_options"]}
            try:
                cover_dict[v["question_text"]]["question_type"] = question_information[v["question_text"]]["question_type"]
            except:
                continue

        return cover_dict
