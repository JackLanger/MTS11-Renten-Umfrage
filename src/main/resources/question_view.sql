create view question_view as
select *
from question
go

grant delete, insert, select, update on question_view to []
go

