package osz.imt.mts.mts11umfrage.models;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import osz.imt.mts.mts11umfrage.dto.UserAnswerDto;

/**
 * User answer entity. Answers given by the user.
 *
 * @author Jacke Langer
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_user_answer")
public class UserAnswer implements DtoTransorm<UserAnswerDto> {

  /**
   * Identity of the Object.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "UUID")
  @ColumnDefault("NEWID()")
  private UUID id;

  /**
   * Question Answer object.
   */
  @ManyToOne
  private QuestionAnswer questionAnswer;

  /**
   * The session id to track wheather the same user answers the question.
   */
  @Column(name = "user_session_id")
  private UUID userId;

  /**
   * The date the answer was given. This is Metadata.
   */
  @Column(name = "date")
  @ColumnDefault("GETDATE()")
  private LocalDateTime date;

  /**
   * Creates a default {@link UserAnswer} for a given user and a Question.
   *
   * @param userid   the user session id
   * @param question the question respective to the answer
   * @return A User Answer object with default values
   */
  public static UserAnswer none(UUID userid, Question question) {

    return new UserAnswer(UUID.randomUUID(), QuestionAnswer.defaultFor(question), userid,
                          LocalDateTime.now());
  }

  @Override
  public UserAnswerDto toDto() {

    return UserAnswerDto.builder()
                        .answerId(this.questionAnswer.getId())
                        .userId(this.userId.toString())
                        .build();
  }

}