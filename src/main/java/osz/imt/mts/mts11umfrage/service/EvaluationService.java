package osz.imt.mts.mts11umfrage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.dto.EvaluationDto;
import osz.imt.mts.mts11umfrage.dto.JsonResponseEvaluationDto;
import osz.imt.mts.mts11umfrage.dto.QuestionInfoDto;
import osz.imt.mts.mts11umfrage.models.Question;
import osz.imt.mts.mts11umfrage.models.UserAnswer;
import osz.imt.mts.mts11umfrage.repository.QuestionRepository;
import osz.imt.mts.mts11umfrage.repository.UserAnswersRepository;

/**
 * .
 *
 * <p>Created by: Jack</p>
 * <p>Date: 17.11.2022</p>
 */
@Service
public class EvaluationService {

  @Autowired
  private UserAnswersRepository userAnswersRepository;
  @Autowired
  private QuestionRepository questionRepository;

  public List<Object> findAll() {

    List<UserAnswer> answers = userAnswersRepository.findAll();
    List<Question> question = questionRepository.findAll();

    List<QuestionInfoDto> questions = new ArrayList<>();
    for (Question qst : question) {
      questions.add(qst.toInfoDto());
    }


    Map<UUID, EvaluationDto> res = new ConcurrentHashMap<>();

    for (UserAnswer answer : answers) {
      int questionId = answer.getQuestionAnswer().getQuestion().getId();
      var eval = EvaluationDto.builder()
                              .userid(answer.getUserId().toString())
                              .build();
      eval.addAnswer(questionId, answer.getQuestionAnswer().getAnswerOption());

      if (res.containsKey(answer.getUserId())) {
        res.get(answer.getUserId()).addAnswer(questionId,
                                              answer.getQuestionAnswer().getAnswerOption());
      } else {

        res.put(answer.getUserId(), eval);
      }
    }

    return List.of(questions, res.values());
  }

  public List<JsonResponseEvaluationDto> createJsonResponse() {

    return createJsonResponse(-1);
  }

  public List<JsonResponseEvaluationDto> createJsonResponse(int index) {

    if (index < 0) {
      List<Question> questions = questionRepository.findAll();
      List<UserAnswer> answers = userAnswersRepository.findAll();
      List<JsonResponseEvaluationDto> result = new ArrayList<>();
      for (Question question : questions) {
        List<String> answerOpts = new ArrayList<>();
        // get al the string values for the questions
        question.getQuestionAnswers().forEach(a -> answerOpts.add(a.getAnswerOption()));
        // filter the answers and sort by the respective question.
        List<String> uAnswers = new ArrayList<>();
        for (UserAnswer answer : answers) {
          if (answer.getQuestionAnswer().getQuestion().getId() == question.getId()) {
            uAnswers.add(answer.getQuestionAnswer().getAnswerOption());
          }
        }
        JsonResponseEvaluationDto resp = JsonResponseEvaluationDto.builder()
                                                                  .questionid(question.getId())
                                                                  .question(
                                                                      question.getQuestionText())
                                                                  .answerOptions(answerOpts)
                                                                  .userAnswers(uAnswers)
                                                                  .build();
        result.add(resp);
      }
      return result;
    }

    throw new NotYetImplementedException("This feature is not yet available!");
  }

}
