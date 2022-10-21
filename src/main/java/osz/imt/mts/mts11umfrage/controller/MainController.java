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
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class MainController {


  /**
   * Name for the submitbutton text object to pass down to the view.
   */
  public static final String SUBMIT_BUTTON_TEXT = "submittButtonText";
  /**
   * userSessionId.
   */
  private static final String USER_SESSION_ID = "userSessionId";
  /**
   * questionId.
   */
  private static final String QUESTION_ID = "questionId";
  /**
   * Next/weiter.
   */
  private static final String NEXT = "weiter";
  /**
   * Thanks/danke. Last question was answered.
   */
  private static final String THANKS = "danke";
  /**
   * questionAnswers.
   */
  private static final String QUESTION_ANSWERS = "questionAnswers";
  /**
   * userAnswers.
   */
  private static final String USER_ANSWER = "userAnswer";
  /**
   * question.
   */
  private static final String QUESTION = "question";

  /**
   * Index endpoint.
   */
  private static final String HOME_ENDPOINT = "/home";

  /**
   * Endpoint for first question.
   */
  private static final String START_ENDPOINT = "/0";
  /**
   * Endpoint for all followup questions.
   */
  private static final String QUESTIONS_ENDPOINT = "/question";

  /**
   * Redirection Endpoint .
   */
  private static final String REDIRECT = "/";

  /**
   * {@link QuestionService} responsible for saving answers and fetching questions.
   */
  private final transient QuestionService service;

  /**
   * Creates a new Controller.
   *
   * @param service the Question service.
   */
  @Autowired
  public MainController(final QuestionService service) {

    this.service = service;
  }

  private static Cookie createCookie(final String name, final String value) {

    final Cookie cookie = new Cookie(name, value);
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
  @GetMapping(HOME_ENDPOINT)
  public ModelAndView landingPage() {

    String disclaimer;
    try {
      disclaimer = Files.readString(Path.of("src/main/resources/cookie-disclaimer.txt"));
    } catch (final IOException e) {
      disclaimer = "could not find a disclaimer please contact an admin.";
    }

    final ModelAndView mav = new ModelAndView("index");
    final String confirmButtonText = "Umfrage starten";
    mav.addObject("disclaimer", disclaimer);
    mav.addObject("confirmBtnTxt", confirmButtonText);
    return mav;
  }

  /**
   * Endpoint for the postrequest from the landing page. This endpoint is responsible for cookie
   * creation and therefore critical.
   *
   * @param response the response from the server {@link HttpServletResponse}
   * @return redirects user to beginning of survey.
   */
  @PostMapping(HOME_ENDPOINT)
  public ModelAndView landingPage(final HttpServletResponse response) {

    // generate and add cookies
    final List<Cookie> cookies = List.of(
        createCookie(USER_SESSION_ID, UUID.randomUUID().toString()),
        createCookie(QUESTION_ID, "1")
    );

    for (final Cookie cookie : cookies) {
      response.addCookie(cookie);
    }

    return startSurvey();
  }

  /**
   * Redirects the user to the last question he visited or to the index page of the website.
   *
   * @param sessionId  the user session id
   * @param questionId the question id
   * @return respective view
   */
  @GetMapping(REDIRECT)
  public ModelAndView redirect(
      @Nullable @CookieValue(name = USER_SESSION_ID) final String sessionId,
      @Nullable @CookieValue(name = QUESTION_ID) final String questionId) {


    final ModelAndView mav;
    // will never be blank or null as checked above.
    if (ValidationUtils.isBlankOrNull(sessionId) || ValidationUtils.isBlankOrNull(questionId)) {
      mav = landingPage();
    } else {
      // question id is not null
      assert questionId != null;
      if (Integer.parseInt(questionId) > 1) {
        mav = question(questionId);
      } else {
        mav = startSurvey();
      }
    }
    return mav;
  }

  /**
   * Endpoint for the first question returns the view to the gender selection.
   *
   * @return {@link ModelAndView} to gender selection
   */
  @GetMapping(START_ENDPOINT)
  public ModelAndView startSurvey() {

    final var question = this.service.findQuestionById(1).get();
    final ModelAndView mav = new ModelAndView(ViewNameUtils.SINGLE_ANSWER);
    mav.addObject(USER_ANSWER, new UserAnswerDto());
    mav.addObject(QUESTION, question);
    mav.addObject(SUBMIT_BUTTON_TEXT, NEXT);
    mav.addObject(QUESTION_ANSWERS, Sex.values());

    return mav;
  }


  /**
   * Question endpoint.
   *
   * @param questionId the question id supplied by the cookies
   * @return ModelAndView containing the current question element
   */
  @GetMapping(QUESTIONS_ENDPOINT)
  public ModelAndView question(@CookieValue(name = QUESTION_ID) final String questionId) {

    final var qstId = Integer.parseInt(questionId);

    final var opt = this.service.findQuestionById(qstId);

    final QuestionDto question = opt.get();
    final QuestionTypes type = question.getQuestionType();

    final String submitbuttonText = qstId == 20 ? THANKS : NEXT;

    return switch (type) {
      case MULTIPLECHOICE -> getQuestion(ViewNameUtils.MULTIPLE_CHOICE, qstId, question,
                                         submitbuttonText);
      case SINGLEANSWER -> getQuestion(ViewNameUtils.SINGLE_ANSWER, qstId, question,
                                       submitbuttonText);
      case INPUT -> getQuestion(ViewNameUtils.INPUT_QUESTION, qstId, question, submitbuttonText);
      case BOOLEAN -> getQuestion(ViewNameUtils.BOOLEAN_QUESTION, qstId, question,
                                  submitbuttonText);
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
  @PostMapping(QUESTIONS_ENDPOINT)
  public ModelAndView questionAnswer(@ModelAttribute final UserAnswerDto userAnswer,
                                     @CookieValue(name = QUESTION_ID) final String questionId,
                                     @CookieValue(name = USER_SESSION_ID) final String sessionId,
                                     final HttpServletResponse response) {

    final int qstId = Integer.parseInt(questionId);
    // save data first update cookie later
    userAnswer.setQuestionId(qstId);
    userAnswer.setUserId(sessionId);
    this.service.saveAnswer(userAnswer);

    // update the cookie value and upload the cookie, this will delete the old cookie and replace
    // it with the new cookie.
    final String qstIdString = String.valueOf(qstId + 1);

    response.addCookie(createCookie(QUESTION_ID, qstIdString));

    return question(qstIdString);
  }


  private ModelAndView getQuestion(final String view, final int id,
                                   final QuestionDto question,
                                   final String submittbuttonText) {

    final ModelAndView mav = new ModelAndView(view);

    mav.addObject(SUBMIT_BUTTON_TEXT, submittbuttonText);
    mav.addObject(QUESTION, question);
    mav.addObject("questioncount", String.format("Frage %s/20", id + 1));
    mav.addObject(USER_ANSWER, new UserAnswerDto());

    return mav;
  }


}
