package osz.imt.mts.mts11umfrage.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osz.imt.mts.mts11umfrage.models.Question;
import osz.imt.mts.mts11umfrage.utils.QuestionTypes;

/**
 * A DTO for the {@link Question} entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDto implements Serializable {

  int id;
  String questionText;
  QuestionTypes questionType;
  List<QuestionAnswerDto> questionAnswers;

}