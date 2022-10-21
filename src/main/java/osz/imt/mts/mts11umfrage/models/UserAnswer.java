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
import org.hibernate.annotations.Comment;

/**
 * User answer entity. Answers given by the user.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_user_answer")
public class UserAnswer {

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
  @Comment("answer value containts the anser qualitative value as well no need to track it here.")
  @ManyToOne
  private QuestionAnswer questionAnswer;

  /**
   * The session id to track wheather the same user answers the question.
   */
  @Column(name = "f_user_session_id")
  private UUID userId;

  /**
   * The date the answer was given. This is Metadata.
   */
  @Column(name = "date")
  @ColumnDefault("GETDATE()")
  private LocalDateTime date;


}