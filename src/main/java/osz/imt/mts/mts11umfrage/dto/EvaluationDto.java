package osz.imt.mts.mts11umfrage.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Dto Model for evaluation, holding the question an the corresponding answer..
 *
 * <p>Created by: Jack</p>
 * <p>Date: 16.11.2022</p>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EvaluationDto {

  public String userid;
  public Map<Integer, List<String>> answers;

  public void addAnswer(int id, String answer) {

    if (answers == null) {
      this.answers = new ConcurrentHashMap<>();
    }

    if (answers.containsKey(id)) {
      answers.get(id).add(answer);
    } else {
      List<String> anw = new ArrayList<>();
      anw.add(answer);
      answers.put(id, anw);
    }
  }

}
