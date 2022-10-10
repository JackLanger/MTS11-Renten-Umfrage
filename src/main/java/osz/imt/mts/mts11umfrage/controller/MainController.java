package osz.imt.mts.mts11umfrage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import osz.imt.mts.mts11umfrage.models.Question;
import osz.imt.mts.mts11umfrage.models.QuestionAnswer;
import osz.imt.mts.mts11umfrage.service.QuestionService;

/**
 * Main Controller of the website.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 10.10.2022</p>
 */
@Controller
public class MainController {

  private final QuestionService service;

  @Autowired
  public MainController(QuestionService service) {

    this.service = service;
  }

  @GetMapping("/")
  public ModelAndView landingPage() {

    var mav = new ModelAndView("index");
    mav.addObject("answer", new QuestionAnswer());
    mav.addObject("question", new Question());
    return mav;
  }

}
