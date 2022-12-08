package osz.imt.mts.mts11umfrage.dto.v2;

import java.util.List;

/**
 * User Answer DTO for v2 json response.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 08.12.2022</p>
 */
public record JsonUserAnswerDto(
    String userSessionId, List<String> userAnswers
) {
}
