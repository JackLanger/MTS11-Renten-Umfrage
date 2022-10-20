package osz.imt.mts.mts11umfrage.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osz.imt.mts.mts11umfrage.data.QuestionTypes;
import osz.imt.mts.mts11umfrage.models.Question;

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
  List<QuestionAnswerDto> questionAnswers = new ArrayList<>();

  /**
   * Adds a new Question answer to the list of answers.
   *
   * @param answerDto the answer dto
   */
  public void addQuestionAnswer(QuestionAnswerDto answerDto) {

    if (questionAnswers == null) {
      questionAnswers = new ArrayList<>();
    }
    
    questionAnswers.add(answerDto);
  }

}