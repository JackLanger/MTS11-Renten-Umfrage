package osz.imt.mts.mts11umfrage.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * .
 *
 * <p>Created by: Jack</p>
 * <p>Date: 07.12.2022</p>
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponseEvaluationDto {

  private int questionid;
  private String question;
  private List<String> answerOptions;
  private List<String> userAnswers;

}
