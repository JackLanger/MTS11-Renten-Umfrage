create view question_view as
select * from t_question
go

grant delete, insert, select, update on question_view to []
go

