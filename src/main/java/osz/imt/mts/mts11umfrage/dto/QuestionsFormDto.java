package osz.imt.mts.mts11umfrage.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * DTO class to send data from website to server. Contains all user answers.
 */
public class QuestionsFormDto {

  /**
   * Question answers for the form.
   */
  private final List<UserAnswerDto> answers;

  /**
   * The users sessionId
   */
  private UUID sessionId;

  public QuestionsFormDto(int size) {

    answers = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      var answer = new UserAnswerDto();
      answer.setQuestionId(i);
      answers.add(answer);
    }
  }

}
