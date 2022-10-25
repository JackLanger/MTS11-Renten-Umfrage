package osz.imt.mts.mts11umfrage.controller;

import static osz.imt.mts.mts11umfrage.controller.utils.Endpoints.HOME_ENDPOINT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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



}
