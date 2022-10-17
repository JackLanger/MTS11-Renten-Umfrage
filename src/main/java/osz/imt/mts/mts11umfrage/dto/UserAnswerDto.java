package osz.imt.mts.mts11umfrage.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO element for a user answer.
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


  private UUID userId;
  private String answerValue;
  private int id;
  private int questionId;

}
