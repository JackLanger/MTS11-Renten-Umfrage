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
import osz.imt.mts.mts11umfrage.models.QuestionAnswer;
import osz.imt.mts.mts11umfrage.models.UserAnswer;
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

  private final QuestionAnswerRepository questionAnswerRepository;
  /**
   * UserData answers repository.
   */
  private final UserAnswersRepository userAnswerRepo;

  /**
   * Creates a new Question service and poplates the respective repositories.
   *
   * @param questionRepo   {@link QuestionRepository}
   * @param userAnswerRepo {@link UserAnswersRepository}
   * @param answerRepo     {@link QuestionAnswerRepository}
   */
  @Autowired
  public QuestionService(QuestionRepository questionRepo,
                         QuestionAnswerRepository answerRepo,
                         UserAnswersRepository userAnswerRepo) {

    this.questionRepo = questionRepo;
    this.userAnswerRepo = userAnswerRepo;
    questionAnswerRepository = answerRepo;
  }

  public Optional<QuestionDto> findQuestionById(int id) {

    var result = questionRepo.findById(id);

    return result.isPresent() ? Optional.of(result.get().toDto()) : Optional.empty();
  }


  public UUID saveAnswer(UserAnswerDto dto) {

    var entity = new UserAnswer();

    // fetch the answer from the questions.
    var answer = questionRepo.findById(dto.getQuestionId())
                             .get()
                             .getQuestionAnswers()
                             .get(dto.getAnswerValue());

    entity.setQuestionAnswer(answer);
    entity.setUserId(UUID.fromString(dto.getUserId()));
    entity.setDate(LocalDateTime.now());

    return userAnswerRepo.save(entity).getUserId();
  }

  public List<Question> findAll() {

    return questionRepo.findAll();

  }

  public int saveQuestion(QuestionDto dto) {

    // create and save the question.
    Question qst = Question.builder().id(dto.getId()).questionAnswers(new ArrayList<>())
                           .questionText(
                               dto.getQuestionText()).questionType(dto.getQuestionType()).build();


    var res = questionRepo.save(qst);


    // save the answers
    for (var answer : dto.getQuestionAnswers()) {

      qst.getQuestionAnswers().add(QuestionAnswer.builder()
                                                 .id(answer.getId())
                                                 .answerOption(answer.getAnswerOption())
                                                 .answerValue(answer.getAnswerValue())
                                                 .htmlType(answer.getHtmlType())
                                                 .question(res).build());
    }

    return res.getId();
  }

}
