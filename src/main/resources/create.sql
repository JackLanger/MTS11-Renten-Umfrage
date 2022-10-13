CREATE TABLE t_question
(
    p_question_id int NOT NULL,
    question_text varchar(255),
    type          int,
    CONSTRAINT pk_t_question PRIMARY KEY (p_question_id)
)
GO


CREATE TABLE t_question_answer
(
    id                     int IDENTITY (1, 1) NOT NULL,
    question_p_question_id int,
    answer_option          varchar(255),
    CONSTRAINT pk_t_question_answer PRIMARY KEY (id)
)
GO

CREATE TABLE t_user_answers
(
    id                 int IDENTITY (1, 1) NOT NULL,
    question_answer_id int,
    user_answer_value  varchar(255)        NOT NULL,
    f_user_session_id  uniqueidentifier,
    CONSTRAINT pk_t_user_answers PRIMARY KEY (id)
)
GO

ALTER TABLE t_user_answers
    ADD CONSTRAINT FK_T_USER_ANSWERS_ON_QUESTION_ANSWER FOREIGN KEY (question_answer_id) REFERENCES t_question_answer (id)
GO

ALTER TABLE t_question_answer
    ADD CONSTRAINT FK_T_QUESTION_ANSWER_ON_QUESTION_P_QUESTION FOREIGN KEY (question_p_question_id) REFERENCES t_question (p_question_id)
GO