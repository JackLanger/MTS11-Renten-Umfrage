package osz.imt.mts.mts11umfrage.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.repository.UserAnswersRepository;
import osz.imt.mts.mts11umfrage.utils.models.UserAnswers;

/**
 * .
 *
 * <p>Created by: Jack</p>
 * <p>Date: 12.10.2022</p>
 */
@Service
public class UserAnswersService {

  private final UserAnswersRepository repository;

  @Autowired
  public UserAnswersService(UserAnswersRepository repository) {

    this.repository = repository;
  }

  public UserAnswers save(UserAnswers answers) {

    return repository.save(answers);
  }

  public int saveAll(List<UserAnswers> answers) {

    int count = 0;
    for (UserAnswers answer : answers) {
      if (repository.save(answer) != null) {
        count++;
      }
    }
    return count;
  }

}
