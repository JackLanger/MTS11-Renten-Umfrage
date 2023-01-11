package osz.imt.mts.mts11umfrage.controller;

import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.DOWNLOAD_ENDPOINT;
import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.HOME_ENDPOINT;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import osz.imt.mts.mts11umfrage.dto.DownloadForm;
import osz.imt.mts.mts11umfrage.models.Question;
import osz.imt.mts.mts11umfrage.repository.QuestionAnswerRepository;
import osz.imt.mts.mts11umfrage.repository.QuestionRepository;
import osz.imt.mts.mts11umfrage.repository.UserAnswersRepository;


/**
 * Main Controller of the website.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 10.10.2022</p>
 *
 * @author Jacek Langer
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
   * Endpoint leading to the download page (download.html).
   *
   * @return {@link ModelAndView} containing a questions and form objects as well as the total
   *     answer count.
   */
  @GetMapping(DOWNLOAD_ENDPOINT)
  public ModelAndView download() {

    final ModelAndView mav = new ModelAndView("download");
    mav.addObject("count", userAnswersRepository.findAllUserAnswerCount());
    List<Question> questions = questionRepository.findAll();
    mav.addObject("questions", questions);
    mav.addObject("indizes", new DownloadForm());
    return mav;
  }

  /**
   * Endpoint leading to the download page (download.html).
   *
   * @return {@link ModelAndView} containing a questions and form objects as well as the total
   *     answer count.
   */
  @GetMapping("/download/data")
  @Deprecated
  public ModelAndView downloadData() {
    // This method is not yet working
    final ModelAndView mav = new ModelAndView("downloadv2");
    mav.addObject("count", userAnswersRepository.findAllUserAnswerCount());
    List<Question> questions = questionRepository.findAll();
    mav.addObject("questions", questions);
    mav.addObject("form", new DownloadForm());
    return mav;
  }

  /**
   * Endpoint for the impressum page. Impressum is seperated from pages but still has to be
   * accessible.
   *
   * @return Impressum.html
   */
  @GetMapping("/impressum")
  public String agreement() {

    return "impressum";
  }


}
