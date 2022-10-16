package osz.imt.mts.mts11umfrage.utils;

import java.util.ArrayList;
import java.util.List;
import osz.imt.mts.mts11umfrage.models.Question;
import osz.imt.mts.mts11umfrage.models.QuestionAnswer;

/**
 * .todo:Delete once Service works
 *
 * <p>Created by: Jack</p>
 * <p>Date: 13.10.2022</p>
 */
public class MockData {

  public static final Question QUESTION = getMockQuestion();

  private static Question getMockQuestion() {

    Question qst = new Question();


    qst.setType(QuestionTypes.MULTIPLECHOICE);
    qst.setQuestionText("Beispielfrage");
    qst.setId(1);

    List<QuestionAnswer> answers = new ArrayList<>();

    for (int i = 0; i < 4; i++) {

      answers.add(new QuestionAnswer(i, qst, String.format("Antwort option %s", i)));
    }

    qst.setQuestionAnswers(answers);

    return qst;
  }

}
