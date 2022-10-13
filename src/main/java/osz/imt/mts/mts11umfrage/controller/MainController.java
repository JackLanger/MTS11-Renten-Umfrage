package osz.imt.mts.mts11umfrage.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import osz.imt.mts.mts11umfrage.service.QuestionService;
import osz.imt.mts.mts11umfrage.service.UserAnswersService;
import osz.imt.mts.mts11umfrage.utils.QuestionTypes;
import osz.imt.mts.mts11umfrage.utils.models.Question;
import osz.imt.mts.mts11umfrage.utils.models.UserAnswers;

/**
 * Main Controller of the website.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 10.10.2022</p>
 */
@Controller
public class MainController {

  private final QuestionService service;
  private final UserAnswersService answerService;

  @Autowired
  public MainController(QuestionService service, UserAnswersService answersService) {

    this.service = service;
    this.answerService = answersService;
  }

  @GetMapping("/")
  public ModelAndView landingPage() {

    var mav = new ModelAndView("index");
    return mav;
  }

  @GetMapping("/0")
  public ModelAndView genericUserData() {

    ModelAndView mav = new ModelAndView("genericdata");

    return mav;
  }

  @PostMapping("/0")
  public ModelAndView genericUserDataSave(@ModelAttribute UserAnswers userAnswers) {

    answerService.save(userAnswers);

    return question(1);
  }

  @GetMapping("/{id}")
  public ModelAndView question(@PathVariable int id) {

    Optional<Question> questionOptional = service.findQuestionById(id);
    Question question = questionOptional.orElseThrow();
    QuestionTypes type = null;

    type = question.getType().getType();

    String submitbuttonText = ++id == 20 ? "danke" : "weiter";

    return switch (type) {
      case MULTIPLECHOICE -> getQuestion("multiplechoice", id, question, submitbuttonText);
      case SINGLEANSWER -> getQuestion("singleAnswer", id, question, submitbuttonText);
      default -> getQuestion("booleanQuestion", id, question, submitbuttonText);
    };
  }

  @PostMapping("/")
  public ModelAndView landingPagePost(Question question) {
    // ..
    return genericUserData();
  }


  private ModelAndView getQuestion(String view, int id, Question question,
                                   String submittbuttonText) {

    ModelAndView mav = new ModelAndView(view);

    mav.addObject("submittButtonText", submittbuttonText);
    mav.addObject("question", question);
    mav.addObject("questioncount", String.format("Frage %s/20", id));
    mav.addObject("id", ++id);

    return mav;
  }


}
