package osz.imt.mts.mts11umfrage.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link osz.imt.mts.mts11umfrage.models.QuestionAnswer} entity
 */
public record QuestionAnswerDto(Integer id, String answerOption, String htmlElement)
    implements Serializable {
}