package osz.imt.mts.mts11umfrage.controller;

import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.DOWNLOAD_ENDPOINT;
import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.ENDPOINT_JSON;
import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.HOME_ENDPOINT;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import osz.imt.mts.mts11umfrage.models.UserAnswer;
import osz.imt.mts.mts11umfrage.repository.QuestionAnswerRepository;
import osz.imt.mts.mts11umfrage.repository.QuestionRepository;
import osz.imt.mts.mts11umfrage.repository.UserAnswersRepository;
import osz.imt.mts.mts11umfrage.service.EvaluationService;


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
  private EvaluationService evaluationService;

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
                        UserAnswersRepository userAnswersRepository,
                        EvaluationService evaluationService) {

    this.questionRepository = questionRepository;
    this.questionAnswerRepository = questionAnswerRepository;
    this.userAnswersRepository = userAnswersRepository;
    this.evaluationService = evaluationService;
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

  //An download Endpoint
  /*@RequestMapping(value="/download", method= RequestMethod.GET)
  public void downloadFile(HttpServletResponse response) throws IOException {
    File file = new File("src/main/resources/cookie-disclaimer.txt");
    InputStream inputStream = new FileInputStream(file);
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
    response.setContentLength((int) file.length());
    FileCopyUtils.copy(inputStream, response.getOutputStream());
  }*/

  /**
   * JSON Endpoint to return all {@link UserAnswer}s as json data.
   *
   * @return List of {@link UserAnswer} as json
   */
  @RequestMapping(value = ENDPOINT_JSON, method = RequestMethod.GET, produces = JSON)
  @ResponseBody
  public List<Object> json() {

    return evaluationService.findAll();
  }

  @GetMapping("/agreement")
  public String agreement() {

    return "agreement";
  }


}
