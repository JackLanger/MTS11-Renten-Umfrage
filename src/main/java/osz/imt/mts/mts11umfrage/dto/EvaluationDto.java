package osz.imt.mts.mts11umfrage.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Dto Model for evaluation, holding the question an the corresponding answer..
 *
 * <p>Created by: Jack</p>
 * <p>Date: 16.11.2022</p>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EvaluationDto {

  /**
   * Id of the Question.
   */
  public int questionId;
  public List<UserAnswerDto> userAnswers;

}
