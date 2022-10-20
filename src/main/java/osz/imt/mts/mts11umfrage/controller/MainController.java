package osz.imt.mts.mts11umfrage.controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
  private static final String HOME = "/";

  /**
   * Endpoint for first question.
   */
  private static final String START = "/0";
  /**
   * Endpoint for all followup questions.
   */
  private static final String QUESTIONS = "/question";
  /**
   * {@link QuestionService} responsible for saving answers and fetching questions.
   */
  private final QuestionService service;
  /**
   * Key is the name while value is the cookie.
   */
  Map<String, Cookie> cookies = new ConcurrentHashMap<>();

  /**
   * Creates a new Controller.
   *
   * @param service the Question service.
   */
  @Autowired
  public MainController(QuestionService service) {

    this.service = service;
  }

  @GetMapping(HOME)
  public ModelAndView landingPage() {

    var mav = new ModelAndView("index");
    return mav;
  }

  @GetMapping(START)
  public ModelAndView startSurvey(HttpServletResponse response) {


    cookies = Map.of(
        USER_SESSION_ID, new Cookie(USER_SESSION_ID, UUID.randomUUID().toString()),
        QUESTION_ID, new Cookie(QUESTION_ID, "1")
    );


    for (Cookie cookie : cookies.values()) {
      cookie.setMaxAge(60 * 60);    // expires in an hour
      cookie.setSecure(true);
      cookie.setHttpOnly(true);   // not available for DOM manipulation
//      cookie.setPath(HOME);       // available everywhere
      response.addCookie(cookie);
    }

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
      case MULTIPLECHOICE -> getQuestion(ViewNameUtils.SINGLE_ANSWER, id, question,
                                         submitbuttonText);
      case SINGLEANSWER -> getQuestion(ViewNameUtils.MULTIPLE_CHOICE, id, question,
                                       submitbuttonText);
      case INPUT -> getQuestion(ViewNameUtils.INPUT_QUESTION, id, question, submitbuttonText);
      case Boolean -> getQuestion(ViewNameUtils.BOOLEAN_QUESTION, id, question, submitbuttonText);
      default -> startSurvey(response);
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
    service.saveAnswer(userAnswer);   // element has no question id present.

    // update the cookie value and upload the cookie, this will delete the old cookie and replace
    // it with the new cookie.
    Cookie cookie = cookies.get(QUESTION_ID);
    cookie.setValue(String.valueOf(++qId));
    response.addCookie(cookie);

    return question(response, String.valueOf(++qId));
  }

  @PostMapping(HOME)
  public ModelAndView landingPagePost(HttpServletResponse response) {
    // ..
    return startSurvey(response);
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
