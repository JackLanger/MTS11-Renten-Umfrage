use umfrage
go

CREATE TABLE dbo.t_question
(
    p_question_id uniqueidentifier default NEWID(),
    question_text varchar(255),
    type          int NOT NULL,
    CONSTRAINT pk_t_question PRIMARY KEY (p_question_id)
)
GO

CREATE TABLE dbo.t_question_answer
(
    p_id                   uniqueidentifier default NEWID(),
    question_p_question_id uniqueidentifier,
    answer_option          varchar(255),
    CONSTRAINT pk_t_question_answer PRIMARY KEY (p_id)
)
GO

CREATE TABLE dbo.t_user_answer
(
    p_id               int IDENTITY (1, 1) NOT NULL,
    question_answer_id uniqueidentifier,
    user_answer_value  varchar(255)        NOT NULL,
    f_user_session_id  uniqueidentifier,
    date               datetime,
    CONSTRAINT pk_t_user_answer PRIMARY KEY (p_id)
)
GO

CREATE TABLE dbo.t_user_data
(
    p_id              uniqueidentifier default NEWID(),
    family_status     int NOT NULL,
    education_level   int NOT NULL,
    employment_status int NOT NULL,
    sex               varchar(255),
    age               int NOT NULL,
    salary            int NOT NULL,
    CONSTRAINT pk_t_user_data PRIMARY KEY (p_id)
)
GO

ALTER TABLE dbo.t_user_data
    ADD CONSTRAINT uc_t_user_data_p UNIQUE (p_id)
GO
ALTER TABLE t_question_answer
    ADD CONSTRAINT FK_T_QUESTION_ANSWER_ON_QUESTION_P_QUESTION FOREIGN KEY (question_p_question_id) REFERENCES t_question (p_question_id)
GO

ALTER TABLE t_user_answer
    ADD CONSTRAINT FK_T_USER_ANSWER_ON_QUESTION_ANSWER FOREIGN KEY (question_answer_id) REFERENCES t_question_answer (p_id)
GO