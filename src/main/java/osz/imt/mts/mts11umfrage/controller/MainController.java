package osz.imt.mts.mts11umfrage.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import osz.imt.mts.mts11umfrage.data.QuestionTypes;
import osz.imt.mts.mts11umfrage.data.Sex;
import osz.imt.mts.mts11umfrage.dto.QuestionDto;
import osz.imt.mts.mts11umfrage.dto.UserAnswerDto;
import osz.imt.mts.mts11umfrage.service.QuestionService;
import osz.imt.mts.mts11umfrage.utils.ValidationUtils;
import osz.imt.mts.mts11umfrage.utils.ViewNameUtils;

/**
 * Main Controller of the website.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 10.10.2022</p>
 */
@Controller
public class MainController {


  public static final String SUBMIT_BUTTON_TEXT = "submittButtonText";
  private static final String USER_SESSION_ID = "userSessionId";
  private static final String QUESTION_ID = "questionId";
  private static final String NEXT = "weiter";
  private static final String THANKS = "danke";
  private static final String QUESTION_ANSWERS = "questionAnswers";
  private static final String USER_ANSWER = "userAnswer";
  private static final String QUESTION = "question";

  /**
   * Index endpoint
   */
  private static final String HOME = "/home";

  /**
   * Endpoint for first question.
   */
  private static final String START = "/0";
  /**
   * Endpoint for all followup questions.
   */
  private static final String QUESTIONS = "/question";
  private static final String REDIRECT = "/";
  /**
   * {@link QuestionService} responsible for saving answers and fetching questions.
   */
  private final QuestionService service;

  /**
   * Creates a new Controller.
   *
   * @param service the Question service.
   */
  @Autowired
  public MainController(QuestionService service) {

    this.service = service;
  }

  private static Cookie createCookie(String name, String value) {

    Cookie cookie = new Cookie(name, value);
    cookie.setMaxAge(60 * 60);    // expires in an hour
    cookie.setSecure(true);
    cookie.setHttpOnly(true);   // not available for DOM manipulation
    cookie.setPath(REDIRECT);       // available everywhere
    return cookie;
  }

  /**
   * Landing page endpoint. This endpoint is the first page the user will see.
   *
   * @return index page.
   */
  @GetMapping(HOME)
  public ModelAndView landingPage() {

    String disclaimer = "disclaimer text from server";
    try {
      disclaimer = Files.readString(Path.of("src/main/resources/cookie-disclaimer.txt"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ModelAndView mav = new ModelAndView("index");
    String confirmButtonText = "Umfrage starten";
    mav.addObject("disclaimer", disclaimer);
    mav.addObject("confirmBtnTxt", confirmButtonText);
    return mav;
  }

  @PostMapping(HOME)
  public ModelAndView landingPage(HttpServletResponse response) {

    ModelAndView mav;
    // generate and add cookies
    List<Cookie> cookies = List.of(
        createCookie(USER_SESSION_ID, UUID.randomUUID().toString()),
        createCookie(QUESTION_ID, "1")
    );

    for (Cookie cookie : cookies) {
      response.addCookie(cookie);
    }

    return startSurvey();
  }

  @GetMapping(REDIRECT)
  public ModelAndView redirect(HttpServletResponse response,
                               @Nullable @CookieValue(name = USER_SESSION_ID) String sessionId,
                               @Nullable @CookieValue(name = QUESTION_ID) String questionId) {

    if (ValidationUtils.isBlankOrNull(sessionId) || ValidationUtils.isBlankOrNull(questionId)) {
      return landingPage();
    }
    // will never be blank or null as checked above.
    int qId;
    qId = Integer.parseInt(questionId);
    if (qId > 1) {
      return question(response, questionId);
    }
    return startSurvey();
  }

  /**
   * Endpoint for the first question returns the view to the gender selection.
   *
   * @return {@link ModelAndView} to gender selection
   */
  @GetMapping(START)
  public ModelAndView startSurvey() {

    var question = service.findQuestionById(1).get();
    ModelAndView mav = new ModelAndView(ViewNameUtils.SINGLE_ANSWER);
    mav.addObject(USER_ANSWER, new UserAnswerDto());
    mav.addObject(QUESTION, question);
    mav.addObject(SUBMIT_BUTTON_TEXT, NEXT);
    mav.addObject(QUESTION_ANSWERS, Sex.values());

    return mav;
  }


  /**
   * Question endpoint
   *
   * @return ModelAndView containing the current question element
   */
  @GetMapping(QUESTIONS)
  public ModelAndView question(HttpServletResponse response,
                               @CookieValue(name = QUESTION_ID) String questionId) {

    var id = Integer.parseInt(questionId);

    var opt = service.findQuestionById(id);

    QuestionDto question = opt.get();
    QuestionTypes type = question.getQuestionType();

    String submitbuttonText = id == 20 ? THANKS : NEXT;

    return switch (type) {
      case MULTIPLECHOICE -> getQuestion(ViewNameUtils.MULTIPLE_CHOICE, id, question,
                                         submitbuttonText);
      case SINGLEANSWER -> getQuestion(ViewNameUtils.SINGLE_ANSWER, id, question,
                                       submitbuttonText);
      case INPUT -> getQuestion(ViewNameUtils.INPUT_QUESTION, id, question, submitbuttonText);
      case Boolean -> getQuestion(ViewNameUtils.BOOLEAN_QUESTION, id, question, submitbuttonText);
      default -> startSurvey();
    };
  }

  /**
   * Post endpoint for the question answers. Responsible for processing the user answer and user
   * data provided by the browser.
   *
   * @param userAnswer the user answer.
   * @param questionId the question id supplied by the browser
   * @param sessionId  session id supplied by the browser
   * @param response   http response.
   * @return redirects to the next question.
   */
  @PostMapping(value = QUESTIONS)
  public ModelAndView questionAnswer(@ModelAttribute UserAnswerDto userAnswer,
                                     @CookieValue(name = QUESTION_ID) String questionId,
                                     @CookieValue(name = USER_SESSION_ID) String sessionId,
                                     HttpServletResponse response) {

    int qId = Integer.parseInt(questionId);
    // save data first update cookie later
    userAnswer.setQuestionId(qId);
    userAnswer.setUserId(sessionId);
    service.saveAnswer(userAnswer);

    // update the cookie value and upload the cookie, this will delete the old cookie and replace
    // it with the new cookie.
    String qIdString = String.valueOf(qId + 1);

    response.addCookie(createCookie(QUESTION_ID, qIdString));

    return question(response, qIdString);
  }


  private ModelAndView getQuestion(String view, int id,
                                   QuestionDto question,
                                   String submittbuttonText) {

    ModelAndView mav = new ModelAndView(view);

    mav.addObject(SUBMIT_BUTTON_TEXT, submittbuttonText);
    mav.addObject(QUESTION, question);
    mav.addObject("questioncount", String.format("Frage %s/20", ++id));
    mav.addObject(USER_ANSWER, new UserAnswerDto());

    return mav;
  }


}
