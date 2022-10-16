package osz.imt.mts.mts11umfrage.service;

import java.util.UUID;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.dto.UserDto;
import osz.imt.mts.mts11umfrage.models.User;
import osz.imt.mts.mts11umfrage.repository.UserRepository;
import osz.imt.mts.mts11umfrage.utils.DtoUtils;

@Service
public class UserService {

  private final UserRepository repository;

  public UserService(UserRepository repository) {this.repository = repository;}

  public UUID save(UserDto userDto) {

    var user = DtoUtils.dtoToEntity(userDto, new User());
    return repository.save(user).getId();
  }

}
