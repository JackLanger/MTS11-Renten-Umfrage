use umfrage
go

CREATE TABLE t_question
(
	id            int IDENTITY (1, 1) NOT NULL,
	question_text varchar(255),
	question_type int,
	CONSTRAINT pk_question PRIMARY KEY (id)
)
GO

CREATE TABLE t_question_answer
(
	id                     int IDENTITY (1, 1) NOT NULL,
	question_p_question_id int,
	answer_option          varchar(255),
	answer_value           int                 NOT NULL,
	CONSTRAINT pk_questionanswer PRIMARY KEY (id)
)
GO
CREATE TABLE t_user_answer
(
	id                 int IDENTITY (1, 1) NOT NULL,
	question_answer_id int,
	user_answer_value  varchar(255)        NOT NULL,
	f_user_session_id  uniqueidentifier,
	date               datetime,
	CONSTRAINT pk_useranswer PRIMARY KEY (id)
)
GO

ALTER TABLE t_question_answer
	ADD CONSTRAINT FK_QUESTIONANSWER_ON_QUESTION_P_QUESTION FOREIGN KEY (question_p_question_id) REFERENCES t_question (id)
GO


ALTER TABLE t_user_answer
	ADD CONSTRAINT FK_USERANSWER_ON_QUESTION_ANSWER FOREIGN KEY (question_answer_id) REFERENCES t_question_answer (id)
GO