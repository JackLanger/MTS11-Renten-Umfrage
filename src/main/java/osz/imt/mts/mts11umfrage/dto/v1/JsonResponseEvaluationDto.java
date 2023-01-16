package osz.imt.mts.mts11umfrage.dto.v1;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osz.imt.mts.mts11umfrage.dto.Evaluation;

/**
 * Response object for Json response of version 1.
 *
 * <p>Example Response</p>
 * <p>
 * {@code { "questionid":1, "question":"QuestionText", "answerOptions":["answer",...],
 * "userAnswers":["answer",...]  } }
 * </p>
 *
 * <p>Created by: Jack</p>
 * <p>Date: 07.12.2022</p>
 *
 * @author Jacek Langer
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponseEvaluationDto implements Evaluation {

  private int questionid;
  private String question;
  private List<String> answerOptions;
  private List<String> userAnswers;

}
