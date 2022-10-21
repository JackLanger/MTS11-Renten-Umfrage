package osz.imt.mts.mts11umfrage.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import osz.imt.mts.mts11umfrage.dto.UserAnswerDto;

@SpringBootTest
class QuestionServiceTest {


  @Autowired
  QuestionService service;

  /**
   * Test for {@link QuestionService#findQuestionById(int)}.
   **/
  @Test
  /*default*/ void testFindById() {

    var res = service.findQuestionById(1);
    assertTrue(res.isPresent());
  }


  /**
   * Test for {@link QuestionService#saveAnswer(UserAnswerDto)}.
   **/
  @Test
  /*default*/ void testSaveUserAnswer() {
    //    assertNotNull(service.saveAnswer());
    // TODO: 19.10.2022 implement.
  }

}