package osz.imt.mts.mts11umfrage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osz.imt.mts.mts11umfrage.models.QuestionAnswer;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Integer> {
}