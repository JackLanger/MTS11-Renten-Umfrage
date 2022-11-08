package osz.imt.mts.mts11umfrage.controller;

import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.HOME_ENDPOINT;
import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.DOWNLOAD_ENDPOINT;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import osz.imt.mts.mts11umfrage.pythonHandler.PythonHandler;



/**
 * Main Controller of the website.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 10.10.2022</p>
 */
@Controller
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class MainController {
  
  /**
   * Landing page endpoint. This endpoint is the first page the user will see.
   *
   * @return index page.
   */
  @GetMapping(HOME_ENDPOINT)
  public ModelAndView landingPage() {

    String disclaimer;
    try {
      disclaimer = Files.readString(Path.of("src/main/resources/cookie-disclaimer.txt"));
    } catch (final IOException e) {
      disclaimer = "could not find a disclaimer please contact an admin.";
    }

    final ModelAndView mav = new ModelAndView("index");
    final String confirmButtonText = "Umfrage starten";
    mav.addObject("disclaimer", disclaimer);
    mav.addObject("confirmBtnTxt", confirmButtonText);
    return mav;
  }

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

  @RequestMapping(value="/download/xlsx", method= RequestMethod.GET)
  public void downloadExcel(HttpServletResponse response) throws IOException {
    PythonHandler pythonHandler = new PythonHandler();
    pythonHandler.runScript();
    File file = new File("src/main/resources/files/Data.xlsx");
    InputStream inputStream = new FileInputStream(file);
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
    response.setContentLength((int) file.length());
    FileCopyUtils.copy(inputStream, response.getOutputStream());
  }

  @RequestMapping(value="/download/json", method= RequestMethod.GET)
  public void downloadJson(HttpServletResponse response) throws IOException {
    PythonHandler pythonHandler = new PythonHandler();
    pythonHandler.runScript();
    File file = new File("src/main/resources/files/Data.json");
    InputStream inputStream = new FileInputStream(file);
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
    response.setContentLength((int) file.length());
    FileCopyUtils.copy(inputStream, response.getOutputStream());
  }



  @RequestMapping(value="/api/data/json", method= RequestMethod.GET, produces = "application/json")
  public void apiResponseDataS(HttpServletResponse response) throws IOException, JSONException {
    PythonHandler pythonHandler = new PythonHandler();
    pythonHandler.runScript();
    String json = Files.readString(Path.of("src/main/resources/files/Data.json"), java.nio.charset.StandardCharsets.ISO_8859_1);
    JSONObject jsonObject = new JSONObject(json);
    response.setContentType("application/json");
    response.getWriter().write(jsonObject.toString(), 0, jsonObject.toString().length());
  }


}
