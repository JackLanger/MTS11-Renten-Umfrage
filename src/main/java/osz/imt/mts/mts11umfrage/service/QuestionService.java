package osz.imt.mts.mts11umfrage.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.dto.QuestionDto;
import osz.imt.mts.mts11umfrage.repository.QuestionAnswerRepository;
import osz.imt.mts.mts11umfrage.repository.QuestionRepository;
import osz.imt.mts.mts11umfrage.repository.UserAnswersRepository;

/**
 * Service responsible for fetching question data.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 10.10.2022</p>
 */
@Service
public class QuestionService {

  /**
   * Questions Repository.
   */
  private final QuestionRepository questionRepo;
  /**
   * UserData answers repository.
   */
  private final UserAnswersRepository userAnswerRepo;

  /**
   * Creates a new Question service and poplates the respective repositories.
   *
   * @param questionRepo   {@link QuestionRepository}
   * @param userAnswerRepo {@link UserAnswersRepository}
   */
  @Autowired
  public QuestionService(QuestionRepository questionRepo,
                         QuestionAnswerRepository answerRepo,
                         UserAnswersRepository userAnswerRepo) {

    this.questionRepo = questionRepo;
    this.userAnswerRepo = userAnswerRepo;
  }

  public Optional<QuestionDto> findQuestionById(int id) {

    var result = questionRepo.findById(id);

    return result.isPresent() ? Optional.of(result.get().toDto()) : Optional.empty();
  }


}
