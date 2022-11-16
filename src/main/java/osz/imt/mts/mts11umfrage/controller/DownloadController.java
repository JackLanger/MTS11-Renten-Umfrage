package osz.imt.mts.mts11umfrage.controller;

import static osz.imt.mts.mts11umfrage.utils.PathUtils.DOWNLOAD_PATH;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import osz.imt.mts.mts11umfrage.pythonHandler.PythonHandler;

/**
 * .
 *
 * <p>Created by: Jack</p>
 * <p>Date: 16.11.2022</p>
 */
public class DownloadController {


  //TODO(Moritz): refactor methods should not return void but a ResponseEntity<Resource>
  // otherwise you need to set the response code.
  private static final String JSON = "application/json";

  /**
   * todo(Moritz): Javadoc.
   *
   * @param response
   * @throws IOException
   */


  HttpHeaders getHeaders() {

    HttpHeaders headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");

    return headers;
  }

  @RequestMapping(value = "/download/xlsx", method = RequestMethod.GET)
  public ResponseEntity<InputStreamResource> downloadExcel(HttpServletResponse response)
      throws IOException {

    PythonHandler pythonHandler = new PythonHandler();
    pythonHandler.runScript();
    File file = new File(DOWNLOAD_PATH + "Data.xlsx");
    InputStream inputStream = new FileInputStream(file);
//    response.setContentType("application/octet-stream");
//    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
//    response.setContentLength((int) file.length());
//    FileCopyUtils.copy(inputStream, response.getOutputStream());
    InputStreamResource resource = new InputStreamResource(inputStream);

    return ResponseEntity.ok()
                         .headers(getHeaders())
                         .contentLength(file.length())
                         .contentType(MediaType.APPLICATION_OCTET_STREAM)
                         .body(resource);
  }

  /**
   * todo(Moritz): javadoc.
   *
   * @param response
   * @return
   * @throws IOException
   */

  @RequestMapping(value = "/download/json", method = RequestMethod.GET)
  public ResponseEntity<InputStreamResource> downloadJson(HttpServletResponse response)
      throws IOException {

    PythonHandler pythonHandler = new PythonHandler();
    pythonHandler.runScript();
    File file = new File(DOWNLOAD_PATH + "Data.json");
    InputStream inputStream = new FileInputStream(file);
//    response.setContentType("application/octet-stream");
//    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
//    response.setContentLength((int) file.length());
//    FileCopyUtils.copy(inputStream, response.getOutputStream());

    InputStreamResource resource = new InputStreamResource(inputStream);

    return ResponseEntity.ok()
                         .headers(getHeaders())
                         .contentLength(file.length())
                         .contentType(MediaType.APPLICATION_OCTET_STREAM)
                         .body(resource);
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
