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
import osz.imt.mts.mts11umfrage.dto.QuestionInfoDto;
import osz.imt.mts.mts11umfrage.dto.v1.JsonResponseEvaluationDto;
import osz.imt.mts.mts11umfrage.dto.v2.JsonResponseEvaluationV2Dto;
import osz.imt.mts.mts11umfrage.dto.v2.JsonUserAnswerDto;
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

  public List<JsonResponseEvaluationDto> createJsonResponseV1() {

    return createJsonResponseV1(-1);
  }


  public Map<String, List<UUID>> getMapedUserAnswersForQuestion(int index) {

    Map<String, List<UUID>> answerMap = new ConcurrentHashMap<>();


    questionRepository.findById(index).ifPresent(q -> {
      q.getQuestionAnswers().forEach(a -> {
        answerMap.put(a.getAnswerOption(), new ArrayList<>());
      });
    });

    List<UserAnswer> result = userAnswersRepository.findByQuestionAnswer_Question_Id(index);
    for (UserAnswer userAnswer : result) {
      answerMap.get(userAnswer.getQuestionAnswer().getAnswerOption()).add(userAnswer.getUserId());
    }


    return answerMap;
  }

  /**
   * Returns V2 json response entities as defined in  {@link JsonResponseEvaluationDto}. Indexing
   * not yet implemented.
   *
   * @param index the index of questions to be returned.
   * @return a list of {@link JsonResponseEvaluationDto}
   */
  public List<JsonResponseEvaluationDto> createJsonResponseV1(int index) {

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


  /**
   * Returns V2 json response entities as defined in  {@link JsonResponseEvaluationV2Dto}. Indexing
   * not yet implemented.
   *
   * @param index the index of questions to be returned.
   * @return a list of {@link JsonResponseEvaluationV2Dto}
   */
  public List<JsonResponseEvaluationV2Dto> createJsonResponseV2(int index) {

    if (index < 0) {
      List<Question> questions = questionRepository.findAll();
      List<UserAnswer> answers = userAnswersRepository.findAll();
      List<JsonResponseEvaluationV2Dto> result = new ArrayList<>();

      for (Question question : questions) {

        List<String> answerOpts = new ArrayList<>();
        // get al the string values for the questions
        question.getQuestionAnswers().forEach(a -> answerOpts.add(a.getAnswerOption()));
        // filter the answers and sort by the respective question.
        List<JsonUserAnswerDto> jsonAnswers = new ArrayList<>();
        List<UserAnswer> questionAnsweredByUser = new ArrayList<>();
        for (UserAnswer answer : answers) {
          // filter the answers and collect only answers for the respective question
          if (answer.getQuestionAnswer().getQuestion().getId() == question.getId()) {
            questionAnsweredByUser.add(answer);
          }
        }
        // build map of answers by user id
        Map<UUID, List<String>> userIdUserAnswersMap = new ConcurrentHashMap<>();
        for (UserAnswer userAnswer : questionAnsweredByUser) {
          UUID key = userAnswer.getUserId();
          if (userIdUserAnswersMap.containsKey(key)) {
            userIdUserAnswersMap.get(key).add(userAnswer.getQuestionAnswer().getAnswerOption());
          } else {
            List<String> userAnswers = new ArrayList<>();
            userAnswers.add(userAnswer.getQuestionAnswer().getAnswerOption());
            userIdUserAnswersMap.put(key, userAnswers);
          }
        }
        // map to dto
        for (Map.Entry<UUID, List<String>> uuidListEntry : userIdUserAnswersMap.entrySet()) {
          jsonAnswers.add(
              new JsonUserAnswerDto(uuidListEntry.getKey().toString(), uuidListEntry.getValue()));
        }


        JsonResponseEvaluationV2Dto resp = JsonResponseEvaluationV2Dto.builder()
                                                                      .questionid(question.getId())
                                                                      .question(
                                                                          question.getQuestionText())
                                                                      .answerOptions(answerOpts)
                                                                      .userAnswers(jsonAnswers)
                                                                      .build();
        result.add(resp);
      }
      return result;
    }

    throw new NotYetImplementedException("This feature is not yet available!");
  }

}
