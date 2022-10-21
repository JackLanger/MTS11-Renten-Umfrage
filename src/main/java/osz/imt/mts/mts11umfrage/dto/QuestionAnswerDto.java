package osz.imt.mts.mts11umfrage.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osz.imt.mts.mts11umfrage.models.QuestionAnswer;

/**
 * A DTO for the {@link QuestionAnswer} entity
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionAnswerDto implements Serializable {

  /**
   * the id of the answer.
   */
  int id;
  /**
   * The string option for the answer.
   */
  String answerOption;
  /**
   * The associated value for the answer.
   */
  int answerValue;
  /**
   * The html type for the question.
   */
  String htmlType;

}