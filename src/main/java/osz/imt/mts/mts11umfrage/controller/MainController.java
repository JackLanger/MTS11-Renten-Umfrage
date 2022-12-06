package osz.imt.mts.mts11umfrage.controller;

import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.DOWNLOAD_ENDPOINT;
import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.ENDPOINT_JSON;
import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.HOME_ENDPOINT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import osz.imt.mts.mts11umfrage.dto.EvaluationDto;
import osz.imt.mts.mts11umfrage.dto.UserAnswerDto;
import osz.imt.mts.mts11umfrage.models.UserAnswer;
import osz.imt.mts.mts11umfrage.repository.QuestionAnswerRepository;
import osz.imt.mts.mts11umfrage.repository.QuestionRepository;
import osz.imt.mts.mts11umfrage.repository.UserAnswersRepository;


/**
 * Main Controller of the website.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 10.10.2022</p>
 */
@Controller
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class MainController {

  private static final String JSON = "application/json";
  /**
   * Repository for questions.
   */
  final QuestionRepository questionRepository;
  /**
   * Repository for questionAnswers.
   */
  final QuestionAnswerRepository questionAnswerRepository;
  /**
   * Repository for userAnswers.
   */
  final UserAnswersRepository userAnswersRepository;

  /**
   * Initializes the respective repositories used for fetching data.
   *
   * @param questionRepository       the {@link QuestionRepository}
   * @param questionAnswerRepository the {@link QuestionAnswerRepository}
   * @param userAnswersRepository    the {@link UserAnswersRepository}
   */
  @Autowired
  public MainController(QuestionRepository questionRepository,
                        QuestionAnswerRepository questionAnswerRepository,
                        UserAnswersRepository userAnswersRepository) {

    this.questionRepository = questionRepository;
    this.questionAnswerRepository = questionAnswerRepository;
    this.userAnswersRepository = userAnswersRepository;
  }

  /**
   * Landing page endpoint. This endpoint is the first page the user will see.
   *
   * @return index page.
   */
  @GetMapping(HOME_ENDPOINT)
  public ModelAndView landingPage() {

    final ModelAndView mav = new ModelAndView("index");
    final String confirmButtonText = "Umfrage starten";
    mav.addObject("confirmBtnTxt", confirmButtonText);
    return mav;
  }

  /**
   * Download UserAnswers.
   *
   * @return
   */
  @GetMapping(DOWNLOAD_ENDPOINT)
  public ModelAndView download() {

    final ModelAndView mav = new ModelAndView("download");
    return mav;
  }

  /**
   * Endpoint for the impressum.
   * @return impressum html
   */
  @GetMapping("/impressum")
  public ModelAndView impressum(){
    return new ModelAndView("impressum");
  }

  /**
   * JSON Endpoint to return all {@link UserAnswer}s as json data.
   *
   * @return List of {@link UserAnswer} as json
   */
  @RequestMapping(value = ENDPOINT_JSON, method = RequestMethod.GET, produces = JSON)
  @ResponseBody
  public List<EvaluationDto> json() {

    List<UserAnswer> answers = userAnswersRepository.findAll();
    List<EvaluationDto> dtos = new ArrayList<>();
    Map<Integer, List<UserAnswerDto>> questions = new ConcurrentHashMap<>();

    for (UserAnswer answer : answers) {
      int questionId = answer.getQuestionAnswer().getQuestion().getId();
      var dto = answer.toDto();
      if (questions.containsKey(questionId)) {
        questions.get(questionId).add(dto);
      } else {
        List<UserAnswerDto> list = new ArrayList<>();
        list.add(dto);
        questions.put(questionId, list);
      }
    }

    for (Map.Entry<Integer, List<UserAnswerDto>> entry : questions.entrySet()) {
      dtos.add(new EvaluationDto(entry.getKey(), entry.getValue()));
    }

    return dtos;
  }

  @GetMapping("/agreement")
  public String agreement() {

    return "agreement";
  }


}
