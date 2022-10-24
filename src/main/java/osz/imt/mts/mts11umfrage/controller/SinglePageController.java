package osz.imt.mts.mts11umfrage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import osz.imt.mts.mts11umfrage.dto.QuestionDto;
import osz.imt.mts.mts11umfrage.dto.SurveyDto;
import osz.imt.mts.mts11umfrage.service.QuestionService;

@Controller
public class SinglePageController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SinglePageController.class);

  @Autowired
  QuestionService service;


  @GetMapping("/questions")
  public ModelAndView showAll() {

    final ModelAndView mav = new ModelAndView("questions");
    final List<QuestionDto> questionsList = this.service.findAll();

    final int count = questionsList.size();
    final SurveyDto questionsForm = new SurveyDto();
    questionsForm.setAnswers(new ArrayList<>());

    mav.addObject("questions", questionsList);
    mav.addObject("form", questionsForm);

    return mav;
  }


  @PostMapping("/questions")
  public ModelAndView createSession(final HttpServletResponse response) {

    final Cookie cookie = new Cookie("mts11-umfrage-session",
                                     UUID.randomUUID().toString());

    response.addCookie(cookie);
    return showAll();
  }

  @PostMapping("/saveData")
  public ModelAndView saveData(@ModelAttribute final SurveyDto surveyAnswers) {


    this.service.saveAllAnswers(surveyAnswers.getAnswers());

    return new ModelAndView("finish");
  }

}
