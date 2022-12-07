package osz.imt.mts.mts11umfrage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import osz.imt.mts.mts11umfrage.models.UserAnswer;

/**
 * Extension for {@link JpaRepository}.
 */
public interface UserAnswersRepository extends JpaRepository<UserAnswer, Integer> {


  @Query("SELECT Count(DISTINCT user_session_id) from UserAnswer ")
  int findAllUserAnswerCount();

}