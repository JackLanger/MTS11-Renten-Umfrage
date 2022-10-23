package osz.imt.mts.mts11umfrage.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.dto.QuestionDto;
import osz.imt.mts.mts11umfrage.dto.UserAnswerDto;
import osz.imt.mts.mts11umfrage.models.Question;
import osz.imt.mts.mts11umfrage.models.UserAnswer;
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
  private final transient QuestionRepository questionRepo;
  /**
   * UserData answers repository.
   */
  private final transient UserAnswersRepository userAnswerRepo;

  /**
   * Creates a new Question service and poplates the respective repositories.
   *
   * @param questionRepo   {@link QuestionRepository}
   * @param userAnswerRepo {@link UserAnswersRepository}
   */
  @Autowired
  public QuestionService(final QuestionRepository questionRepo,
                         final UserAnswersRepository userAnswerRepo) {

    this.questionRepo = questionRepo;
    this.userAnswerRepo = userAnswerRepo;
  }

  /**
   * find a question for a given id.
   *
   * @param id the id of the question
   * @return Optional containing the Question object or empty.
   */
  public Optional<QuestionDto> findQuestionById(final int id) {

    final var result = this.questionRepo.findById(id);

    return result.isPresent() ? Optional.of(result.get().toDto()) : Optional.empty();
  }

  /**
   * Save the user answer to the db.
   *
   * @param dto the user answer dto
   * @return the user answer uuid.
   */
  public UUID saveAnswer(final UserAnswerDto dto) {

    final var entity = new UserAnswer();

    // fetch the answer from the questions.
    final var answer = this.questionRepo.findById(dto.getQuestionId())
                                        .get()
                                        .getQuestionAnswers()
                                        .get(dto.getAnswerValue());

    entity.setQuestionAnswer(answer);
    entity.setUserId(UUID.fromString(dto.getUserId()));
    entity.setDate(LocalDateTime.now());

    return this.userAnswerRepo.save(entity).getUserId();
  }

  /**
   * Returns all questions from the database.
   *
   * @return {@link List} of {@link Question}.
   */
  public List<QuestionDto> findAll() {

    List<Question> questions = this.questionRepo.findAll();
    List<QuestionDto> dtos = new ArrayList<>();

    for (Question question : questions) {
      dtos.add(question.toDto());
    }
    return dtos;

  }


}
