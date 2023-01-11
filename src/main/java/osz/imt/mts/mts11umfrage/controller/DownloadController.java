package osz.imt.mts.mts11umfrage.controller;

import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.DOWNLOAD_EXCEL;
import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.ENDPOINT_CSV;
import static osz.imt.mts.mts11umfrage.utils.PathConstants.CSV_DOWNLOAD_PATH;
import static osz.imt.mts.mts11umfrage.utils.PathConstants.XLSX_DOWNLOAD_PATH;

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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import osz.imt.mts.mts11umfrage.dto.Evaluation;
import osz.imt.mts.mts11umfrage.models.UserAnswer;
import osz.imt.mts.mts11umfrage.pythonHandler.PythonHandler;
import osz.imt.mts.mts11umfrage.service.AuthService;
import osz.imt.mts.mts11umfrage.service.EvaluationService;

/**
 * Download Controller, responsible for managing download endpoints.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 16.11.2022</p>
 *
 * @author Moritz Hartmann, Jacek Langer
 */
@Controller
public class DownloadController {


  /**
   * Constant to be set for response for controller endpoints producing json responses.
   */
  public static final String JSON = "application/json";
  /**
   * Evaluation Service to handle Evaluation tasks.
   */
  private final EvaluationService evalService;
  /**
   * Authentication service to handle authentication.
   */
  private final AuthService authService;
  /**
   * Python handler, responsible for generating csv and xlsx files.
   */
  private final PythonHandler pythonHandler;

  /**
   * Creates a new Download controller. This should not be used as Springboot autowires needed
   * services.
   *
   * @param evalService   The Evaluation Service
   * @param authService   the Authentication Service
   * @param pythonHandler the python handler
   */
  @Autowired
  public DownloadController(EvaluationService evalService,
                            AuthService authService,
                            PythonHandler pythonHandler) {

    this.evalService = evalService;
    this.authService = authService;
    this.pythonHandler = pythonHandler;
  }


  HttpHeaders getHeaders(String filename) {

    HttpHeaders headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");
    headers.add("Content-Disposition", "attachment; filename=" + filename);

    return headers;
  }

  /**
   * Endpoint to provide a download for a xlsx file. Basically this endpoint provides a Filestream
   * to the user.
   *
   * @param token    Token used for authentication
   * @param response {@link HttpServletResponse} from the server containing cookies etc.
   * @return {@link ResponseEntity} containing a {@link InputStreamResource}.
   * @throws IOException              A {@link IOException} is thrown if the file to be provided is
   *                                  not to be found
   * @throws NoSuchAlgorithmException if the hashing is not a valid hashing algorithm.
   */
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

  /**
   * Endpoint to provide a download for a CSV file. Basically this endpoint provides a Filestream to
   * the user.
   *
   * @param token    Token used for authentication
   * @param response {@link HttpServletResponse} from the server containing cookies etc.
   * @return {@link ResponseEntity} containing a {@link InputStreamResource}.
   * @throws IOException              A {@link IOException} is thrown if the file to be provided is
   *                                  not to be found
   * @throws NoSuchAlgorithmException if the hashing is not a valid hashing algorithm.
   */
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
   * Shorthand endpoint to return the respective <strong>Json Response Entity Objects</strong> for
   * all questions dependent on the version provided.
   *
   * @param token   the token provided by the user
   * @param version the verion to use "v1" or "v2"
   * @return {@link ResponseEntity} containing a List of <strong>Json Response Entity
   *     Objects</strong> as json
   * @throws NoSuchAlgorithmException If hashing is not possible with the algorithm used
   */
  @RequestMapping(value = "/json/{version}", method = RequestMethod.GET, produces = JSON)
  @ResponseBody
  public ResponseEntity<List<? extends Evaluation>> json(@RequestParam String token,
                                                         @PathVariable String version)
      throws NoSuchAlgorithmException {

    return jsonById(token, version, null);
  }


  /**
   * Endpoint to return all {@link UserAnswer} objects associated with a given question. The
   * response is dependent of the version and index provided.
   * <p>
   * Possible options for the version are:
   *
   *   <ul>
   *     <li>v1: {@link osz.imt.mts.mts11umfrage.dto.v1.JsonResponseEvaluationDto}</li>
   *     <li>v2: {@link osz.imt.mts.mts11umfrage.dto.v2.JsonResponseEvaluationV2Dto}</li>
   *   </ul>
   * Dependent of the given version a list of the respective response format is appended to the
   * {@link ResponseEntity}
   *
   * @param token   Validation token provided by user
   * @param version version of the json response expected. e.g. "v1"
   * @param index   the index of the question to be returned. All answers for the question are
   *                returned. If the value is null all Questions are considered.
   * @return A {@link ResponseEntity} containing the respective <strong>Json Response Evaluation
   *     Dto</strong>.
   * @throws NoSuchAlgorithmException If hashing using the provided algorithm is not possible
   */
  @RequestMapping(value = "/json/{version}/{index}", method = RequestMethod.GET, produces = JSON)
  @ResponseBody
  public ResponseEntity<List<? extends Evaluation>> jsonById(@RequestParam String token,
                                                             @PathVariable String version,
                                                             @PathVariable @Nullable String index)
      throws NoSuchAlgorithmException {

    if (authService.verifyToken(token)) {

      int i = index == null ? -1 : Integer.parseInt(index);
      var resource = switch (version) {
        default -> evalService.createJsonResponseV1();
        case "v2" -> evalService.createJsonResponseV2(i);
      };
      return ResponseEntity.ok()
                           .contentType(MediaType.APPLICATION_JSON)
                           .body(resource);
    }
    return ResponseEntity.status(401).build();
  }

  /**
   * Endpoint to provide a download for a CSV file containing only to the question with the provided
   * id. Basically this endpoint provides a Filestream to the user.
   *
   * @param token Token used for authentication
   * @param index index of the question to map
   * @return {@link ResponseEntity} containing a {@link InputStreamResource}.
   * @throws NoSuchAlgorithmException if the hashing is not a valid hashing algorithm.
   */
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
