package osz.imt.mts.mts11umfrage.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osz.imt.mts.mts11umfrage.dto.util.QuestionTypes;
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

  /**
   * The id.
   */
  private int id;
  /**
   * The question text.
   */
  private String questionText;
  /**
   * The type of the question. {@link QuestionTypes}
   */
  private QuestionTypes questionType;
  /**
   * {@link List} of {@link osz.imt.mts.mts11umfrage.dto.QuestionAnswerDto}.
   */
  private List<QuestionAnswerDto> questionAnswers = new ArrayList<>();

  /**
   * Adds a new Question answer to the list of answers.
   *
   * @param answerDto the answer dto
   */
  public void addQuestionAnswer(final QuestionAnswerDto answerDto) {

    if (this.questionAnswers == null) {
      this.questionAnswers = new ArrayList<>();
    }

    this.questionAnswers.add(answerDto);
  }


}