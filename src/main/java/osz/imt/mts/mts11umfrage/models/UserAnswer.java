package osz.imt.mts.mts11umfrage.models;

import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_user_answer")
public class UserAnswer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "question_answer_id")
  private QuestionAnswer questionAnswer;

  @Basic(fetch = FetchType.LAZY)
  @Column(nullable = false)
  private String userAnswerValue;

  @Column(name = "f_user_session_id")
  private UUID userId;

}