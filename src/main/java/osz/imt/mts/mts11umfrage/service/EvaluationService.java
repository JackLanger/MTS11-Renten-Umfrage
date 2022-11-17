package osz.imt.mts.mts11umfrage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.dto.EvaluationDto;
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
                              .questionId(answer.getQuestionAnswer().getQuestion().getId())
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

}
