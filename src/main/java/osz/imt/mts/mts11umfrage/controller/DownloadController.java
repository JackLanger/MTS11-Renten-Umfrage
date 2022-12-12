package osz.imt.mts.mts11umfrage.controller;

import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.DOWNLOAD_EXCEL;
import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.ENDPOINT_CSV;
import static osz.imt.mts.mts11umfrage.utils.PathUtils.CSV_DOWNLOAD_PATH;
import static osz.imt.mts.mts11umfrage.utils.PathUtils.XLSX_DOWNLOAD_PATH;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import osz.imt.mts.mts11umfrage.dto.Evaluation;
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

      pythonHandler.runScript("xlsx");
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

  @RequestMapping(value = ENDPOINT_CSV, method = RequestMethod.POST)
  public ResponseEntity<InputStreamResource> downloadCsv(@RequestParam String token,
                                                         HttpServletResponse response)
      throws IOException, NoSuchAlgorithmException {

    if (authService.verifyToken(token)) {
      pythonHandler.runScript("csv");
      File file = new File(CSV_DOWNLOAD_PATH);
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
  @RequestMapping(value = "/json/{version}", method = RequestMethod.GET, produces = JSON)
  @ResponseBody
  public ResponseEntity<List<? extends Evaluation>> json(@RequestParam String token,
                                                         @PathVariable String version)
      throws NoSuchAlgorithmException {

    if (authService.verifyToken(token)) {

      var resource = switch (version) {
        default -> evalService.createJsonResponseV1();
        case "v2" -> evalService.createJsonResponseV2(-1);
      };
      return ResponseEntity.ok()
                           .contentType(MediaType.APPLICATION_JSON)
                           .body(resource);


    }
    return ResponseEntity.status(401).build();
  }


  @RequestMapping("/csv/{index}")
  public ResponseEntity<Object> csv(@RequestParam String token,
                                    @PathVariable String index) throws NoSuchAlgorithmException {

    var result = evalService.getMapedUserAnswersForQuestion(Integer.parseInt(index));

    if (authService.verifyToken(token)) {
      String[][] table = new String[result.size()][];

      int i = 0;
      for (Map.Entry<String, List<UUID>> stringListEntry : result.entrySet()) {
        String tableHead = stringListEntry.getKey();
        List<String> valuesAndHead = new ArrayList<>();
        valuesAndHead.add(tableHead);
        for (UUID uuid : stringListEntry.getValue()) {
          valuesAndHead.add(uuid.toString());
        }
        table[i++] = valuesAndHead.toArray(String[]::new);
      }

      return ResponseEntity.ok()
                           .contentType(MediaType.APPLICATION_JSON)
                           .body(table);
    }
    return ResponseEntity.status(401).build();
  }

}
