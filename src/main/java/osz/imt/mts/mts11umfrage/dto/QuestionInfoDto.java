package osz.imt.mts.mts11umfrage.dto;

import java.util.List;
import osz.imt.mts.mts11umfrage.data.QuestionTypes;

/**
 * .
 *
 * <p>Created by: Jack</p>
 * <p>Date: 17.11.2022</p>
 */
public record QuestionInfoDto(int id, String text, QuestionTypes type, List<String> answers) {
}
