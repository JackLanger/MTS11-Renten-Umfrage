package osz.imt.mts.mts11umfrage.models;

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
import osz.imt.mts.mts11umfrage.data.QuestionTypes;
import osz.imt.mts.mts11umfrage.dto.QuestionAnswerDto;
import osz.imt.mts.mts11umfrage.dto.QuestionDto;

/**
 * Data model of a Question used in the survey.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 10.10.2022</p>
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_question")
public class Question {

  @Id
//  @Column(name = "p_question_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String questionText;
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
  private List<QuestionAnswer> questionAnswers = new java.util.ArrayList<>();
  @Enumerated(EnumType.ORDINAL)
  private QuestionTypes questionType;

  /**
   * Returns the Dto Version of this entry.
   *
   * @return {@link QuestionAnswerDto}
   */
  public QuestionDto toDto() {

    var dto = QuestionDto.builder()
                         .id(this.id)
                         .questionText(this.getQuestionText())
                         .questionType(this.questionType)
                         .build();

    for (QuestionAnswer questionAnswer : questionAnswers) {
      dto.addQuestionAnswer(questionAnswer.toDto());
    }
    return dto;
  }

}
