package osz.imt.mts.mts11umfrage.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osz.imt.mts.mts11umfrage.dto.QuestionAnswerDto;
import osz.imt.mts.mts11umfrage.dto.QuestionDto;
import osz.imt.mts.mts11umfrage.dto.QuestionInfoDto;
import osz.imt.mts.mts11umfrage.dto.util.QuestionTypes;

/**
 * Data model of a Question used in the survey.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 10.10.2022</p>
 *
 * @author Jacek Langer
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_question")
public class Question implements DtoTransorm<QuestionDto> {

  /**
   * Public key.
   */
  @Id
  //  @Column(name = "p_question_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  /**
   * The question text.
   */
  private String questionText;
  /**
   * {@link List} of {@link QuestionAnswer}.
   */
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
  private List<QuestionAnswer> questionAnswers = new java.util.ArrayList<>();
  /**
   * Question type. e.g. {@link QuestionTypes#MULTIPLECHOICE}.
   */
  @Enumerated(EnumType.ORDINAL)
  private QuestionTypes questionType;

  /**
   * Returns the Dto Version of this entry.
   *
   * @return {@link QuestionAnswerDto}
   */
  public QuestionDto toDto() {

    final var dto = QuestionDto.builder()
                               .id(this.id)
                               .questionText(this.getQuestionText())
                               .questionType(this.questionType)
                               .build();

    for (final QuestionAnswer questionAnswer : this.questionAnswers) {
      dto.addQuestionAnswer(questionAnswer.toDto());
    }
    return dto;
  }

  /**
   * Maps this Question object to a {@link QuestionInfoDto}.
   *
   * @return {@link QuestionInfoDto} with mapped values from this {@link Question}.
   */
  public QuestionInfoDto toInfoDto() {

    List<String> answers = new ArrayList<>();
    if (questionAnswers.isEmpty()) {
      answers.add("keine");
    } else {
      for (QuestionAnswer questionAnswer : this.questionAnswers) {
        answers.add(questionAnswer.getAnswerOption());
      }
    }

    return new QuestionInfoDto(this.id, this.questionText, this.questionType, answers);
  }

}
