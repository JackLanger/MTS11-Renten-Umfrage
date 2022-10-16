package osz.imt.mts.mts11umfrage.dto;

import java.io.Serializable;
import java.util.List;
import osz.imt.mts.mts11umfrage.models.Question;
import osz.imt.mts.mts11umfrage.utils.QuestionTypes;

/**
 * A DTO for the {@link Question} entity.
 */
public record QuestionDto(int id, String questionText, QuestionTypes questionType,
                          List<QuestionAnswerDto> questionAnswers)
    implements Serializable {
}