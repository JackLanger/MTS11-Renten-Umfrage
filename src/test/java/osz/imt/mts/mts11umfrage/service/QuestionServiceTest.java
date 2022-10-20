package osz.imt.mts.mts11umfrage.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}