package osz.imt.mts.mts11umfrage.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import osz.imt.mts.mts11umfrage.models.UserAnswer;

/**
 * Extension for {@link JpaRepository}.
 *
 * @author Jacke Langer
 */
public interface UserAnswersRepository extends JpaRepository<UserAnswer, Integer> {

  /**
   * Select all Question Answers for the question with the given id.
   *
   * @param id the id of the question
   * @return A List of {@link UserAnswer} for the given id
   */
  List<UserAnswer> findByQuestionAnswer_Question_Id(int id);

  /**
   * Select and return the count of all distinct user answers. Distinct answers are Defined by a
   * distinct user session ID. Therefore, a set of answers with the same user session ID is regarded
   * as a single answer.
   *
   * @return Count of distinct user answers
   */
  @Query("SELECT Count(DISTINCT user_session_id) from UserAnswer ")
  int findAllUserAnswerCount();

}