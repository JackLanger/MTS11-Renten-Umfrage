package osz.imt.mts.mts11umfrage.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
 * Service Responsible for fetching data and mapping it to evaluation objects that are returned  by
 * the respective endpoint.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 17.11.2022</p>
 *
 * @author Jacek Langer
 */
@Service
public class EvaluationService {

  /**
   * The {@link UserAnswersRepository}.
   */
  private final UserAnswersRepository userAnswersRepository;
  /**
   * The {@link QuestionRepository}.
   */
  private final QuestionRepository questionRepository;

  /**
   * Creates a new {@link EvaluationService} and autowires the containing services.
   *
   * @param userAnswersRepository the {@link UserAnswersRepository}
   * @param questionRepository    the {@link QuestionRepository}
   */
  @Autowired
  public EvaluationService(UserAnswersRepository userAnswersRepository,
                           QuestionRepository questionRepository) {

    this.userAnswersRepository = userAnswersRepository;
    this.questionRepository = questionRepository;
  }

  /**
   * Find and return all the user answers.
   *
   * @return user answers as {@link List} of {@link Object}
   */
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

  /**
   * Creates a V1 Json Response of all questions by mapping questions against the V1
   * {@link JsonResponseEvaluationDto}.
   *
   * @return List of {@link JsonResponseEvaluationDto}
   */
  public List<JsonResponseEvaluationDto> createJsonResponseV1() {

    return createJsonResponseV1(-1);
  }

  /**
   * Returns a map of user answers with a list of {@link UUID} where the key is the question and the
   * value is the List of User Session Ids of entries answered to the respective question.
   *
   * @param index The index of the question
   * @return Map of Question and List of uuid where the question is the key and the value is a list
   *     of user session ids.
   */
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
    // todo: needs refactoring!!
    List<Question> questions;
    List<UserAnswer> answers;
    List<JsonResponseEvaluationV2Dto> result = new ArrayList<>();
    List<Integer> multipleChoiceIndices = List.of(8, 15, 23);
    List<UUID> allIds = new ArrayList<>();
    if (multipleChoiceIndices.contains(index)) {
      var res = userAnswersRepository.findByQuestionAnswer_Question_Id(1);
      for (UserAnswer a : res) {
        allIds.add(a.getUserId());
      }
    }

    if (index < 0) {
      answers = userAnswersRepository.findAll();
      questions = questionRepository.findAll();
    } else {
      questions = questionRepository.findById(index).stream().toList();
      answers = userAnswersRepository.findByQuestionAnswer_Question_Id(index);
    }

    for (Question question : questions) {

      List<String> answerOpts = new ArrayList<>();
      // get al the string values for the questions
      question.getQuestionAnswers().forEach(a -> answerOpts.add(a.getAnswerOption()));
      // filter the answers and sort by the respective question.
      List<JsonUserAnswerDto> jsonAnswers = new ArrayList<>();
      List<UserAnswer> questionAnsweredByUser = new ArrayList<>();
      if (allIds.isEmpty()) {

        for (UserAnswer answer : answers) {
          // filter the answers and collect only answers for the respective question
          if (answer.getQuestionAnswer().getQuestion().getId() == question.getId()) {
            questionAnsweredByUser.add(answer);
          }
        }
      } else {
        Set<UUID> uuids = new HashSet<>();
        for (UserAnswer answer : answers) {
          // filter the answers and collect only answers for the respective question
          if (answer.getQuestionAnswer().getQuestion().getId() == question.getId()) {
            questionAnsweredByUser.add(answer);
            uuids.add(answer.getUserId());
          }
        }
        allIds.removeAll(uuids);
        var qst = questionRepository.findById(index);
        for (UUID id : allIds) {
          questionAnsweredByUser.add(UserAnswer.none(id, qst.get()));
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

}
