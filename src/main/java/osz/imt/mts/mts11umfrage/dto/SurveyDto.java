package osz.imt.mts.mts11umfrage.dto;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO class to send data from website to server. Contains all user answers.
 */

@Getter
@Setter
public class SurveyDto {

  /**
   * Question answers for the form.
   */
  private List<SurveyAnswerDto> answers;

  /**
   * The users sessionId
   */
  private UUID sessionId;

}
