package osz.imt.mts.mts11umfrage.controller;

import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.DOWNLOAD_EXCEL;
import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.DOWNLOAD_JSON;
import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.ENDPOINT_JSON;
import static osz.imt.mts.mts11umfrage.utils.PathUtils.DOWNLOAD_PATH;
import static osz.imt.mts.mts11umfrage.utils.PathUtils.XLSX_DOWNLOAD_PATH;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import osz.imt.mts.mts11umfrage.dto.JsonResponseEvaluationDto;
import osz.imt.mts.mts11umfrage.models.UserAnswer;
import osz.imt.mts.mts11umfrage.pythonHandler.PythonHandler;
import osz.imt.mts.mts11umfrage.repository.UserAnswersRepository;
import osz.imt.mts.mts11umfrage.service.AuthService;
import osz.imt.mts.mts11umfrage.service.EvaluationService;

/**
 * .
 *
 * <p>Created by: Jack</p>
 * <p>Date: 16.11.2022</p>
 */
@Controller
public class DownloadController {


  //TODO(Moritz): refactor methods should not return void but a ResponseEntity<Resource>
  // otherwise you need to set the response code.
  public static final String JSON = "application/json";

  private final EvaluationService evalService;
  private final AuthService authService;
  private final PythonHandler pythonHandler;
  private final UserAnswersRepository userAnswerRepo;

  @Autowired
  public DownloadController(EvaluationService evalService,
                            AuthService authService,
                            PythonHandler pythonHandler, UserAnswersRepository userAnswerRepo) {

    this.evalService = evalService;
    this.authService = authService;
    this.pythonHandler = pythonHandler;
    this.userAnswerRepo = userAnswerRepo;
  }


  HttpHeaders getHeaders(String filename) {

    HttpHeaders headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");
    headers.add("Content-Disposition", "attachment; filename=" + filename);

    return headers;
  }

  @RequestMapping(value = DOWNLOAD_EXCEL, method = RequestMethod.POST)
  public ResponseEntity<InputStreamResource> downloadExcel(@RequestParam String token,
                                                           HttpServletResponse response)
      throws IOException, NoSuchAlgorithmException {

    if (authService.verifyToken(token)) {

      pythonHandler.runScript();
      File file = new File(XLSX_DOWNLOAD_PATH);
      InputStream inputStream = new FileInputStream(file);
      InputStreamResource resource = new InputStreamResource(inputStream);

      return ResponseEntity.ok()
                           .headers(getHeaders(file.getName()))
                           .contentLength(file.length())
                           .contentType(MediaType.APPLICATION_OCTET_STREAM)
                           .body(resource);
    }
    return ResponseEntity.status(401).build();
  }

  /**
   * todo(Moritz): javadoc.
   *
   * @param response
   * @return
   * @throws IOException
   */

  @RequestMapping(value = DOWNLOAD_JSON, method = RequestMethod.POST)
  public ResponseEntity<InputStreamResource> downloadJson(@RequestParam String token,
                                                          HttpServletResponse response)
      throws IOException, NoSuchAlgorithmException {

    if (authService.verifyToken(token)) {
      pythonHandler.runScript();
      // create json file as temp file
      int count = userAnswerRepo.findAllUserAnswerCount();
      Path jsonPath = Path.of(
          String.format("%s/jsonData-%d.json", DOWNLOAD_PATH, count));
      File file = jsonPath.toFile();
      file.deleteOnExit();

      Files.createFile(jsonPath);
      Gson gson = new Gson();
      // marshall data to string and write to file
      String jsonString = gson.toJson(evalService.createJsonResponse());
      Files.write(jsonPath, jsonString.getBytes());

//      File file = new File(JSON_DOWNLOAD_PATH);
      InputStream inputStream = new FileInputStream(file);

      InputStreamResource resource = new InputStreamResource(inputStream);

      return ResponseEntity.ok()
                           .headers(getHeaders(file.getName()))
                           .contentLength(file.length())
                           .contentType(MediaType.APPLICATION_OCTET_STREAM)
                           .body(resource);
    }
    return ResponseEntity.status(401).build();
  }

  /**
   * JSON Endpoint to return all {@link UserAnswer}s as json data.
   *
   * @return List of {@link UserAnswer} as json
   */
  @RequestMapping(value = ENDPOINT_JSON, method = RequestMethod.GET, produces = JSON)
  @ResponseBody
  public List<JsonResponseEvaluationDto> json(@RequestParam String token)
      throws NoSuchAlgorithmException {

    if (authService.verifyToken(token)) {
      List<JsonResponseEvaluationDto> response;

      return evalService.createJsonResponse();
    }
    return null;
  }

}
