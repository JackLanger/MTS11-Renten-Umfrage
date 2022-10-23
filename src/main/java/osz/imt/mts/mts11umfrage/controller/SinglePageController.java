package osz.imt.mts.mts11umfrage.controller;

import java.util.List;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import osz.imt.mts.mts11umfrage.dto.QuestionDto;
import osz.imt.mts.mts11umfrage.dto.QuestionsFormDto;
import osz.imt.mts.mts11umfrage.service.QuestionService;

@Controller
public class SinglePageController {

  @Autowired
  QuestionService service;


  @GetMapping("/all")
  public ModelAndView showAll(HttpServletResponse response) {

    ModelAndView mav = new ModelAndView("questions");
    List<QuestionDto> questionsList = service.findAll();
    QuestionsFormDto questionsForm = new QuestionsFormDto(questionsList.size());
    mav.addObject("questions", questionsList);
    mav.addObject("form", questionsForm);
    Cookie cookie = new Cookie("mts11-umfrage-session",
                               UUID.randomUUID().toString());

    response.addCookie(cookie);
    return mav;
  }

}
