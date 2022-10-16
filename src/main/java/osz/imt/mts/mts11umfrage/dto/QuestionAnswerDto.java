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

  int id;
  String answerOption;
  String htmlElement;

}