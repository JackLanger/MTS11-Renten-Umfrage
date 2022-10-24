package osz.imt.mts.mts11umfrage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO element for a user_data answer.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 13.10.2022</p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserAnswerDto {

  /**
   * The User id, this is used to validate the question and find users that did not finish the whole
   * survey.
   */
  private String userId;
  /**
   * The answer given for the given quesstion. The value reflects the entry index/quality value of
   * the answer.
   */
  private int answerValue;
  /**
   * Id of the answer given.
   */
  private int answerId;
  /**
   * The Id of the question.
   */
  private int questionId;


}
