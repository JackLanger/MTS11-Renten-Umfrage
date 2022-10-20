package osz.imt.mts.mts11umfrage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osz.imt.mts.mts11umfrage.models.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}