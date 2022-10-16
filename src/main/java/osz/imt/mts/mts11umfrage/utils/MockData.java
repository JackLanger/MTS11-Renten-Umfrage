package osz.imt.mts.mts11umfrage.utils;

import java.util.ArrayList;
import java.util.List;
import osz.imt.mts.mts11umfrage.dto.QuestionAnswerDto;
import osz.imt.mts.mts11umfrage.dto.QuestionDto;

/**
 * .todo:Delete once Service works
 *
 * <p>Created by: Jack</p>
 * <p>Date: 13.10.2022</p>
 */
public class MockData {

  public static final QuestionDto QUESTION = getMockQuestion();

  private static QuestionDto getMockQuestion() {

    QuestionDto qst = new QuestionDto();


    qst.setQuestionType(QuestionTypes.MULTIPLECHOICE);
    qst.setQuestionText("Beispielfrage");
    qst.setId(1);

    List<QuestionAnswerDto> answers = new ArrayList<>();

    for (int i = 0; i < 4; i++) {

      answers.add(new QuestionAnswerDto(i, String.format("Antwort option %s", i), "checkbox"));
    }

    qst.setQuestionAnswers(answers);

    return qst;
  }

}
