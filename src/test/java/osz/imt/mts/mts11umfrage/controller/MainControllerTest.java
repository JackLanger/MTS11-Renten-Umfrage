package osz.imt.mts.mts11umfrage.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for the main controller to test correct behavior of the controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

  // TODO: 21.10.2022 Test weather the correct endpoint is reached and the expected response is
  //  returned.

  /**
   * Response code if response is ok.
   */
  private static final int OK = 200;
  /**
   * Response code if an ERROR occurred.
   */
  private static final int ERROR = 404;
  /**
   * Response code for Access denied.
   */
  private static final int DENIED = 500;


  /**
   * Test if landing page is reached.
   **/
  @Test
  /* default */ void testLandingPage() {
// TODO: 21.10.2022 implement
  }

  /**
   * Test if landing page is reached.
   **/
  @Test
  /* default */ void testRedirect() {
// TODO: 21.10.2022 implement
  }

  /**
   * Test if last question is reached.
   **/
  @Test
  /* default */ void testRedirectToLastQuestion() {
// TODO: 21.10.2022 implement
  }

  /**
   * Test if gender selection is reached.
   **/
  @Test
  /* default */ void testStartSurvey() {
// TODO: 21.10.2022 implement
  }

  /**
   * Test if correct question is reached and returned.
   **/
  @Test
  /* default */ void testQuestion() {
// TODO: 21.10.2022 implement
  }

  /**
   * Test if question answer is submitted.
   **/
  @Test
  /* default */ void testQuestionAnswer() {
// TODO: 21.10.2022 implement
  }

}