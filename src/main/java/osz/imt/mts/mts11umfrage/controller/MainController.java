package osz.imt.mts.mts11umfrage.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import osz.imt.mts.mts11umfrage.data.EducationLevel;
import osz.imt.mts.mts11umfrage.data.EmploymentStatus;
import osz.imt.mts.mts11umfrage.data.FamiliyStatus;
import osz.imt.mts.mts11umfrage.data.QuestionTypes;
import osz.imt.mts.mts11umfrage.data.SaleryCategory;
import osz.imt.mts.mts11umfrage.data.Sex;
import osz.imt.mts.mts11umfrage.dto.QuestionDto;
import osz.imt.mts.mts11umfrage.dto.UserAnswerDto;
import osz.imt.mts.mts11umfrage.dto.UserDataDto;
import osz.imt.mts.mts11umfrage.models.Question;
import osz.imt.mts.mts11umfrage.service.QuestionService;
import osz.imt.mts.mts11umfrage.service.UserAnswersService;
import osz.imt.mts.mts11umfrage.service.UserManagementService;
import osz.imt.mts.mts11umfrage.service.UserService;
import osz.imt.mts.mts11umfrage.utils.MockData;

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
  private final UserManagementService userManager;
  private final UserService userService;

  @Autowired
  public MainController(QuestionService service, UserAnswersService answersService,
                        UserManagementService userManager, UserService userService) {

    this.service = service;
    this.answerService = answersService;
    this.userManager = userManager;
    this.userService = userService;
  }

  @GetMapping("/")
  public ModelAndView landingPage() {

    var mav = new ModelAndView("index");
    return mav;
  }

  @GetMapping("/0")
  public ModelAndView genericUserData() {

    ModelAndView mav = new ModelAndView("userdata");
    mav.addObject("user", new UserDataDto());
    mav.addObject("familyStatus", FamiliyStatus.values());
    mav.addObject("sex", Sex.values());
    mav.addObject("education", EducationLevel.values());
    mav.addObject("employment", EmploymentStatus.values());
    mav.addObject("salary", SaleryCategory.values());
    mav.addObject("twentyfive", LocalDate.now().minusYears(25));
    mav.addObject("max", LocalDate.now());

    return mav;
  }

  @PostMapping("/0")
  public ModelAndView genericUserDataSave(@ModelAttribute UserDataDto user) {

    userManager.store(userService.save(user));
    return question(0);
  }


  /**
   * Question endpoint
   *
   * @param id of the previous question
   * @return ModelAndView containing the current question element
   */
  @GetMapping("/question")
  public ModelAndView question(int id) {

//    var opt = service.findQuestionById(id);
//
//    opt.orElseThrow();

    List<QuestionDto> questions = new ArrayList<>();
    questions.add(MockData.QUESTION);

    QuestionDto question = questions.get(id);
    QuestionTypes type = question.getQuestionType();

    String submitbuttonText = id == 20 ? "danke" : "weiter";

    return switch (type) {
      case MULTIPLECHOICE -> getQuestion("multiplechoice", id, question, submitbuttonText);
      case SINGLEANSWER -> getQuestion("singleanswer", id, question, submitbuttonText);
      case INPUT -> getQuestion("inputquestion", id, question, submitbuttonText);
      case Boolean -> getQuestion("booleanQuestion", id, question, submitbuttonText);
      default -> genericUserData();
    };
  }

  @PostMapping(value = "/question")
  public ModelAndView questionAnswer(@ModelAttribute UserAnswerDto answer) {

//    answerService.save(answer);
    int id = answer.getId() + 1;
    return question(id);
  }

  @PostMapping("/")
  public ModelAndView landingPagePost(Question question) {
    // ..
    return genericUserData();
  }


  private ModelAndView getQuestion(String view, int id, QuestionDto question,
                                   String submittbuttonText) {

    ModelAndView mav = new ModelAndView(view);

    UserAnswerDto answer = UserAnswerDto.builder().questionId(id)
                                        .userId(userManager.getUserId())
                                        .build();

    mav.addObject("submittButtonText", submittbuttonText);
    mav.addObject("question", question);
    mav.addObject("questioncount", String.format("Frage %s/20", ++id));
    mav.addObject("answer", answer);
    mav.addObject("id", id);

    return mav;
  }


}
