package osz.imt.mts.mts11umfrage.service;

import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.dto.UserDataDto;
import osz.imt.mts.mts11umfrage.models.UserData;
import osz.imt.mts.mts11umfrage.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository repository;

  public UserService(UserRepository repository) {this.repository = repository;}


  public UUID save(UserDataDto userDto) {

    var user = new UserData();
    BeanUtils.copyProperties(userDto, user);
    return repository.save(user).getId();
  }

}
