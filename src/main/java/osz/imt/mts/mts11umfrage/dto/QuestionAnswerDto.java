package osz.imt.mts.mts11umfrage.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osz.imt.mts.mts11umfrage.models.QuestionAnswer;

/**
 * A DTO for the {@link QuestionAnswer} entity.
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
  private int id;
  /**
   * The string option for the answer.
   */
  private String answerOption;
  /**
   * The associated value for the answer.
   */
  private int answerValue;
  /**
   * The html type for the question.
   */
  private String htmlType;

}