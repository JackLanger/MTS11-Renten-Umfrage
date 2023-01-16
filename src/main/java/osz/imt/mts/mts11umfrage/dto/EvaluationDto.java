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
 *
 * @author Jacek Langer
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EvaluationDto {

  /**
   * The user session ID.
   */
  public String userid;
  /**
   * Map of question ids and lists of user answers.
   *
   * <ul>
   * <li>k = question id </li>
   * <li>V = list of User answers</li>
   * </ul>
   */
  public Map<Integer, List<String>> answers;

  /**
   * Appends an answer to the answers map. If the provided id is not yet registered in the Map a new
   * entry will be appended to the map, otherwise the entry is retrived and a new value is appended
   * to the list of answers.
   *
   * @param id     Question id
   * @param answer the answer to append.
   */
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
