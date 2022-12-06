package osz.imt.mts.mts11umfrage.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.dto.EvaluationDto;
import osz.imt.mts.mts11umfrage.dto.UserAnswerDto;
import osz.imt.mts.mts11umfrage.models.Question;
import osz.imt.mts.mts11umfrage.models.UserAnswer;
import osz.imt.mts.mts11umfrage.repository.QuestionRepository;
import osz.imt.mts.mts11umfrage.repository.UserAnswersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JsonResponse {
    @Autowired
    UserAnswersRepository userAnswersRepository;
    @Autowired
    QuestionRepository questionRepository;
    public List<EvaluationDto> json() {

        List<UserAnswer> answers = userAnswersRepository.findAll();
        List<EvaluationDto> dtos = new ArrayList<>();
        Map<Integer, List<UserAnswerDto>> questions = new ConcurrentHashMap<>();

        for (UserAnswer answer : answers) {
            int questionId = answer.getQuestionAnswer().getQuestion().getId();
            var dto = answer.toDto();
            if (questions.containsKey(questionId)) {
                questions.get(questionId).add(dto);
            } else {
                List<UserAnswerDto> list = new ArrayList<>();
                list.add(dto);
                questions.put(questionId, list);
            }
        }

        for (Map.Entry<Integer, List<UserAnswerDto>> entry : questions.entrySet()) {
            dtos.add(new EvaluationDto(entry.getKey(), entry.getValue()));
        }

        return dtos;
    }



}
