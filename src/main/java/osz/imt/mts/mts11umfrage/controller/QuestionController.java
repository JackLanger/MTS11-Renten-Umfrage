package osz.imt.mts.mts11umfrage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import osz.imt.mts.mts11umfrage.dto.QuestionDto;
import osz.imt.mts.mts11umfrage.dto.SurveyAnswerDto;
import osz.imt.mts.mts11umfrage.dto.SurveyDto;
import osz.imt.mts.mts11umfrage.dto.UserAnswerDto;
import osz.imt.mts.mts11umfrage.service.QuestionService;

@Controller
public class QuestionController {

  private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

  @Autowired
  QuestionService service;


  /**
   * Question endpoint responsible for generationg the questions for the user.
   *
   * @return questions view.
   */
  @GetMapping("/questions")
  public ModelAndView showAll() {

    final ModelAndView mav = new ModelAndView("questions");
    final List<QuestionDto> questionsList = this.service.findAll();

    final int count = questionsList.size();
    final SurveyDto questionsForm = new SurveyDto();
    questionsForm.setAnswers(new ArrayList<>());

//    for (int i = 0; i < questionsList.size(); i++) {
//      final var answer = new SurveyAnswerDto();
//      answer.setQuestionId(questionsList.get(i).getId());
//      questionsForm.getAnswers().add(answer);
//    }

    mav.addObject("questions", questionsList);
    mav.addObject("form", questionsForm);

    return mav;
  }


  /**
   * Send the user to the questions after generating a user id that is stored in memory to veryfi
   * current user has not yet submitted answers.
   *
   * @param response the response from the server to provide a cookie to the user.
   * @return question endpoint
   */
  @PostMapping("/questions")
  public ModelAndView createSession(final HttpServletResponse response) {

    final Cookie cookie = createCookie("mts11-umfrage-session",
                                       UUID.randomUUID().toString());

//    response.addCookie(cookie);
    return showAll();
  }

  /**
   * Endpoint for saving the data to the database.
   *
   * @param surveyAnswers the answers retrieved from the server
   * @return goodbye and thank you endpoint.
   */
  @PostMapping("/saveData")
  public ModelAndView saveData(@ModelAttribute final SurveyDto surveyAnswers,
                               @Nullable @CookieValue("mts11-umfrage-session") String sessionId) {
    String session = Objects.isNull(sessionId) ? UUID.randomUUID().toString() :sessionId;

    final var answerList = surveyAnswers.getAnswers();

    final var userAnswerDtos = new ArrayList<UserAnswerDto>();
    for (final SurveyAnswerDto surveyAnswerDto : answerList) {
      if (isNotNull(surveyAnswerDto.getMultianswerOptIds())) {
        // multiple answers are present (multiple choice)
        for (final int val : surveyAnswerDto.getMultianswerOptIds()) {
          userAnswerDtos.add(UserAnswerDto.builder()
                                          .answerId(val)
                                          .userId(session)
                                          .build());
        }
        // radio button answer.
      } else if (isNotNull(surveyAnswerDto.getAnswerOptId())) {
        userAnswerDtos.add(UserAnswerDto.builder()
                                        .userId(session)
                                        .answerId(surveyAnswerDto.getAnswerOptId())
                                        .build());
      }
    }
    this.service.saveAllAnswers(userAnswerDtos);
    return finishedSurvey();
  }


  @GetMapping("/Finish")
  public ModelAndView finishedSurvey() {

    return new ModelAndView("finish");
  }

  private boolean isNotNull(final Object o) {

    return Objects.nonNull(o);
  }


  private static Cookie createCookie(final String name, final String value) {

    final Cookie cookie = new Cookie(name, value);
    cookie.setMaxAge(60 * 60);    // expires in an hour
    cookie.setSecure(true);
    cookie.setHttpOnly(true);   // not available for DOM manipulation
    cookie.setPath("/");       // available everywhere
    return cookie;
  }

}
