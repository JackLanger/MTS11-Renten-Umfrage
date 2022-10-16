package osz.imt.mts.mts11umfrage.dto;

import java.io.Serializable;
import osz.imt.mts.mts11umfrage.models.QuestionAnswer;

/**
 * A DTO for the {@link QuestionAnswer} entity
 */
public record QuestionAnswerDto(Integer id, String answerOption, String htmlElement)
    implements Serializable {
}