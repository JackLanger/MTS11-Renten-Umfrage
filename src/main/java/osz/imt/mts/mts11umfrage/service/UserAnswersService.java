package osz.imt.mts.mts11umfrage.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.models.UserAnswer;
import osz.imt.mts.mts11umfrage.repository.UserAnswersRepository;

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

  public UserAnswer save(UserAnswer answers) {

    return repository.save(answers);
  }

  public int saveAll(List<UserAnswer> answers) {

    int count = 0;
    for (UserAnswer answer : answers) {
      if (repository.save(answer) != null) {
        count++;
      }
    }
    return count;
  }

}
