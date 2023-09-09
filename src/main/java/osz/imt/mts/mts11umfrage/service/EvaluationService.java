package osz.imt.mts.mts11umfrage.service;

import java.util.*;
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
//TODO: identify invariant behavior and extract into Base class
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
   * PK of multiple choice question.
   */
  private static final int QUESTION_8 = 8;

  /**
   * PK of multiple choice question.
   */
  private static final int QUESTION_15 = 15;

  /**
   * PK of multiple choice question.
   */
  private static final int QUESTION_23 = 23;


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
      EvaluationDto eval = buildEvaluationDto(answer);
      eval.addAnswer(questionId, answer.getQuestionAnswer().getAnswerOption());
      appendAnswerOptionsToMap(answer, res, questionId, eval);
    }

    return List.of(questions, res.values());
  }


  private static void appendAnswerOptionsToMap(UserAnswer answer, Map<UUID, EvaluationDto> res, int questionId, EvaluationDto eval) {

    if (res.containsKey(answer.getUserId())) {
      res.get(answer.getUserId()).addAnswer(questionId,
          answer.getQuestionAnswer().getAnswerOption());
    } else {
      res.put(answer.getUserId(), eval);
    }
  }


  private static EvaluationDto buildEvaluationDto(UserAnswer answer) {

    return EvaluationDto.builder()
        .userid(answer.getUserId().toString())
        .build();
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
   * of user session ids.
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

    if (isFindAll(index)) {
      List<Question> questions = questionRepository.findAll();
      List<UserAnswer> answers = userAnswersRepository.findAll();
      List<JsonResponseEvaluationDto> result = new ArrayList<>();
      addAllQuestionsToResult(questions, answers, result);
      return result;
    }

    throw new NotYetImplementedException("This feature is not yet available!");
  }


  private static void addAllQuestionsToResult(List<Question> questions, List<UserAnswer> answers, List<JsonResponseEvaluationDto> result) {

    for (Question question : questions) {
      List<String> answerOpts = new ArrayList<>();
      List<String> userAnswers = new ArrayList<>();
      addAllStringValuesTo(answerOpts, question);
      addAllUserAnswers(answers, question, userAnswers);
      JsonResponseEvaluationDto resp = buildEvaluationDto(question, answerOpts, userAnswers);
      result.add(resp);
    }
  }


  private static void addAllUserAnswers(List<UserAnswer> answers, Question question, List<String> userAnswers) {

    for (UserAnswer answer : answers) {
      if (answer.getQuestionAnswer().getQuestion().getId() == question.getId()) {
        appendAnswerToUser(userAnswers, answer);
      }
    }
  }


  private static JsonResponseEvaluationDto buildEvaluationDto(Question question, List<String> answerOpts, List<String> uAnswers) {

    JsonResponseEvaluationDto resp = JsonResponseEvaluationDto.builder()
        .questionid(question.getId())
        .question(
            question.getQuestionText())
        .answerOptions(answerOpts)
        .userAnswers(uAnswers)
        .build();
    return resp;
  }


  private static boolean isFindAll(int index) {

    return index < 0;
  }


  /**
   * Returns V2 json response entities as defined in  {@link JsonResponseEvaluationV2Dto}. Indexing
   * not yet implemented.
   *
   * @param index the index of questions to be returned.
   * @return a list of {@link JsonResponseEvaluationV2Dto}
   */
  public List<JsonResponseEvaluationV2Dto> createJsonResponseV2(int index) {

    List<Question> questions;
    List<UserAnswer> answers;
    List<JsonResponseEvaluationV2Dto> result = new ArrayList<>();
    List<Integer> multipleChoiceIndices = List.of(QUESTION_8, QUESTION_15, QUESTION_23);
    List<UUID> allUserAnswerIds = new ArrayList<>();

    if (multipleChoiceIndices.contains(index)) {
      var userAnswerResult = userAnswersRepository.findByQuestionAnswer_Question_Id(1);
      addAllUserAnswerIds(userAnswerResult, allUserAnswerIds);
    }

    if (isShowAll(index)) {
      answers = userAnswersRepository.findAll();
      questions = questionRepository.findAll();
    } else {
      questions = questionRepository.findById(index).stream().toList();
      answers = userAnswersRepository.findByQuestionAnswer_Question_Id(index);
    }

    processAllQuestions(index, questions, allUserAnswerIds, answers, result);
    return result;

  }


  private void processAllQuestions(int index, List<Question> questions, List<UUID> allUserAnswerIds, List<UserAnswer> answers, List<JsonResponseEvaluationV2Dto> result) {

    for (Question question : questions) {

      List<String> answerOpts = new ArrayList<>();
      addAllStringValuesTo(answerOpts, question);
      List<JsonUserAnswerDto> jsonAnswers = new ArrayList<>();
      List<UserAnswer> questionAnsweredByUser = new ArrayList<>();

      if (allUserAnswerIds.isEmpty()) {
        collectAnswersByQuestionIdFromAllAnswers(question, answers, questionAnsweredByUser);
      } else {
        Set<UUID> uuids = filterAnswersByQuestionId(question, answers, questionAnsweredByUser);
        allUserAnswerIds.removeAll(uuids);
        getQuestionByIndexAndAddAllUserAnswersById(index, allUserAnswerIds, questionAnsweredByUser);
      }

      Map<UUID, List<String>> userIdUserAnswersMap = buildMapOfUserAnswers(questionAnsweredByUser);
      // map to dto
      createDtoFromMap(userIdUserAnswersMap, jsonAnswers);
      JsonResponseEvaluationV2Dto resp = buildJsonResponseForEvaluationV2Dto(question, answerOpts, jsonAnswers);
      result.add(resp);
    }
  }


  private static void addAllStringValuesTo(List<String> answerOpts, Question question) {

    question.getQuestionAnswers().forEach(a -> answerOpts.add(a.getAnswerOption()));
  }


  private static void createDtoFromMap(Map<UUID, List<String>> userIdUserAnswersMap, List<JsonUserAnswerDto> jsonAnswers) {

    for (Map.Entry<UUID, List<String>> uuidListEntry : userIdUserAnswersMap.entrySet()) {
      jsonAnswers.add(
          new JsonUserAnswerDto(uuidListEntry.getKey().toString(), uuidListEntry.getValue()));
    }
  }


  private static JsonResponseEvaluationV2Dto buildJsonResponseForEvaluationV2Dto(Question question, List<String> answerOpts, List<JsonUserAnswerDto> jsonAnswers) {

    JsonResponseEvaluationV2Dto resp = JsonResponseEvaluationV2Dto.builder()
        .questionid(question.getId())
        .question(
            question.getQuestionText())
        .answerOptions(answerOpts)
        .userAnswers(jsonAnswers)
        .build();
    return resp;
  }


  private static Map<UUID, List<String>> buildMapOfUserAnswers(List<UserAnswer> userAnswers) {

    Map<UUID, List<String>> userIdToUserAnswers = new ConcurrentHashMap<>();
    addAllUserAnswers(userAnswers, userIdToUserAnswers);
    return userIdToUserAnswers;
  }


  private static void addAllUserAnswers(List<UserAnswer> questionAnsweredByUser, Map<UUID, List<String>> userIdToUserAnswers) {

    for (UserAnswer userAnswer : questionAnsweredByUser) {
      UUID key = userAnswer.getUserId();
      mapUserAnswerToKey(userAnswer, userIdToUserAnswers, key);
    }
  }


  private static void mapUserAnswerToKey(UserAnswer userAnswer, Map<UUID, List<String>> userIdToUserAnswers, UUID key) {

    if (userIdToUserAnswers.containsKey(key)) {
      appendAnswerToUser(userIdToUserAnswers.get(key), userAnswer);
    } else {
      addNewUserAnswerEntry(userAnswer, userIdToUserAnswers, key);
    }
  }


  private static void addNewUserAnswerEntry(UserAnswer userAnswer, Map<UUID, List<String>> userIdToUserAnswers, UUID key) {

    List<String> userAnswers = new ArrayList<>();
    appendAnswerToUser(userAnswers, userAnswer);
    userIdToUserAnswers.put(key, userAnswers);
  }


  private static void appendAnswerToUser(List<String> userIdToUserAnswers, UserAnswer userAnswer) {

    userIdToUserAnswers.add(userAnswer.getQuestionAnswer().getAnswerOption());
  }


  private void getQuestionByIndexAndAddAllUserAnswersById(int index, List<UUID> allUserAnswerIds, List<UserAnswer> questionAnsweredByUser) {

    var questionByIndex = questionRepository.findById(index);
    addAllUserAnswersByIdToQuestion(allUserAnswerIds, questionAnsweredByUser, questionByIndex);
  }


  private static void addAllUserAnswersByIdToQuestion(List<UUID> allUserAnswerIds, List<UserAnswer> questionAnsweredByUser, Optional<Question> questionByIndex) {

    for (UUID id : allUserAnswerIds) {
      questionAnsweredByUser.add(UserAnswer.none(id, questionByIndex.get()));
    }
  }


  private static Set<UUID> filterAnswersByQuestionId(Question question, List<UserAnswer> answers, List<UserAnswer> questionAnsweredByUser) {

    Set<UUID> uuids = new HashSet<>();
    for (UserAnswer answer : answers) {
      // filter the answers and collect only answers for the respective question
      if (answer.getQuestionAnswer().getQuestion().getId() == question.getId()) {
        questionAnsweredByUser.add(answer);
        uuids.add(answer.getUserId());
      }
    }
    return uuids;
  }


  private static void collectAnswersByQuestionIdFromAllAnswers(Question question, List<UserAnswer> answers, List<UserAnswer> questionAnsweredByUser) {

    for (UserAnswer answer : answers) {
      // filter the answers and collect only answers for the respective question
      if (answer.getQuestionAnswer().getQuestion().getId() == question.getId()) {
        questionAnsweredByUser.add(answer);
      }
    }
  }

  private static void addAllUserAnswerIds(List<UserAnswer> res, List<UUID> allUserAnswerIds) {

    for (UserAnswer answer : res) {
      allUserAnswerIds.add(answer.getUserId());
    }
  }

  private static boolean isShowAll(int index) {

    return isFindAll(index);
  }

}
