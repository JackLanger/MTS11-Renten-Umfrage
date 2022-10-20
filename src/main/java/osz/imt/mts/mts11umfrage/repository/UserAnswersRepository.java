package osz.imt.mts.mts11umfrage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osz.imt.mts.mts11umfrage.models.UserAnswer;

public interface UserAnswersRepository extends JpaRepository<UserAnswer, Integer> {
}