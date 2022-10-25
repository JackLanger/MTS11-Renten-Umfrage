package osz.imt.mts.mts11umfrage.dto;

import lombok.Data;

/**
 * Survey Form answer DTO.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 24.10.2022</p>
 */
@Data
public class SurveyAnswerDto {
  
  /**
   * The value of the answer selected.
   */
  private Integer answerOptId;
  /**
   * array of all answers selected.
   */
  private int[] multianswerOptIds;

}
