package osz.imt.mts.mts11umfrage.controller;

import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.DOWNLOAD_ENDPOINT_JSON;
import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.ENDPOINT_JSON;
import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.HOME_ENDPOINT;
import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.DOWNLOAD_ENDPOINT;
import static osz.imt.mts.mts11umfrage.utils.PathUtils.DOWNLOAD_PATH;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import osz.imt.mts.mts11umfrage.models.UserAnswer;
import osz.imt.mts.mts11umfrage.pythonHandler.PythonHandler;
import osz.imt.mts.mts11umfrage.repository.QuestionAnswerRepository;
import osz.imt.mts.mts11umfrage.repository.QuestionRepository;
import osz.imt.mts.mts11umfrage.repository.UserAnswersRepository;


/**
 * Main Controller of the website.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 10.10.2022</p>
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
   * Download UserAnswers.
   *
   * @return
   */
  @GetMapping(DOWNLOAD_ENDPOINT)
  public ModelAndView download() {

    final ModelAndView mav = new ModelAndView("download");
    return mav;
  }

  //An download Endpoint
  /*@RequestMapping(value="/download", method= RequestMethod.GET)
  public void downloadFile(HttpServletResponse response) throws IOException {
    File file = new File("src/main/resources/cookie-disclaimer.txt");
    InputStream inputStream = new FileInputStream(file);
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
    response.setContentLength((int) file.length());
    FileCopyUtils.copy(inputStream, response.getOutputStream());
  }*/

  /**
   * JSON Endpoint to return all {@link UserAnswer}s as json data.
   *
   * @return List of {@link UserAnswer} as json
   */
  @RequestMapping(value = ENDPOINT_JSON, method = RequestMethod.GET, produces = JSON)
  @ResponseBody
  public List<UserAnswer> json() {

    return userAnswersRepository.findAll();
  }

  /**
   * Download Endpoint for a json file containing all {@link UserAnswer}s.
   *
   * @return download for a file.
   */
  @RequestMapping(value = DOWNLOAD_ENDPOINT_JSON, method = RequestMethod.GET, produces = JSON)
  @ResponseBody
  public byte[] jsonDownload() {
    // TODO: 10.11.2022 implement
    return null;
  }

  /**
   * todo(Moritz): Javadoc.
   *
   * @param response
   * @throws IOException
   */

  @RequestMapping(value = "/download/xlsx", method = RequestMethod.GET)
  public void downloadExcel(HttpServletResponse response) throws IOException {

    PythonHandler pythonHandler = new PythonHandler();
    pythonHandler.runScript();
    File file = new File(DOWNLOAD_PATH + "Data.xlsx");
    InputStream inputStream = new FileInputStream(file);
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
    response.setContentLength((int) file.length());
    FileCopyUtils.copy(inputStream, response.getOutputStream());
  }

  /**
   * todo(Moritz): javadoc.
   *
   * @param response
   * @throws IOException
   */

  @RequestMapping(value = "/download/json", method = RequestMethod.GET)
  public void downloadJson(HttpServletResponse response) throws IOException {

    PythonHandler pythonHandler = new PythonHandler();
    pythonHandler.runScript();
    File file = new File(DOWNLOAD_PATH + "Data.json");
    InputStream inputStream = new FileInputStream(file);
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
    response.setContentLength((int) file.length());
    FileCopyUtils.copy(inputStream, response.getOutputStream());
  }


  /**
   * todo(Moritz):javadoc.
   *
   * @param response
   * @throws IOException
   * @throws JSONException
   */

  @RequestMapping(value = "/api/data/json", method = RequestMethod.GET,
      produces = JSON)
  public void apiResponseData(HttpServletResponse response) throws IOException, JSONException {

    PythonHandler pythonHandler = new PythonHandler();
    pythonHandler.runScript();
    String json = Files.readString(Path.of(DOWNLOAD_PATH + "Data.json"),
                                   java.nio.charset.StandardCharsets.ISO_8859_1);
    JSONObject jsonObject = new JSONObject(json);
    response.setContentType("application/json");
    response.getWriter().write(jsonObject.toString(), 0, jsonObject.toString().length());
  }

}
