package osz.imt.mts.mts11umfrage.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osz.imt.mts.mts11umfrage.dto.QuestionAnswerDto;
import osz.imt.mts.mts11umfrage.utils.HtmlTypes;

/**
 * Question answer entity.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_question_answer")
public class QuestionAnswer implements DtoTransorm<QuestionAnswerDto> {

  /**
   * Primary key for the option. Is Integer value but can be changed to a UUID if need be.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  /**
   * The Question of the answer option. Question to Answer is best described as a parent child
   * relation.
   */
  @ManyToOne
  @JoinColumn(name = "question_p_question_id")
  private Question question;
  /**
   * String value for the option.
   */
  private String answerOption;
  /**
   * Qualitative value for the option.
   */
  private int answerValue;

  /**
   * Type value for the html to display.
   */
  private String htmlType = HtmlTypes.TEXT;

  /**
   * Returns the Dto Version of this entry.
   *
   * @return {@link QuestionAnswerDto}
   */
  public QuestionAnswerDto toDto() {

    return QuestionAnswerDto.builder()
                            .id(this.getId())
                            .answerOption(this.getAnswerOption())
                            .answerValue(this.getAnswerValue())
                            .htmlType(this.getHtmlType())
                            .build();
  }

}