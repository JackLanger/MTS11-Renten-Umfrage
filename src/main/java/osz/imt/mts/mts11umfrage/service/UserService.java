package osz.imt.mts.mts11umfrage.service;

import java.util.UUID;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.data.EducationLevel;
import osz.imt.mts.mts11umfrage.data.EmploymentStatus;
import osz.imt.mts.mts11umfrage.data.FamiliyStatus;
import osz.imt.mts.mts11umfrage.data.SaleryCategory;
import osz.imt.mts.mts11umfrage.data.Sex;
import osz.imt.mts.mts11umfrage.dto.UserDataDto;
import osz.imt.mts.mts11umfrage.models.UserData;
import osz.imt.mts.mts11umfrage.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository repository;

  public UserService(UserRepository repository) {this.repository = repository;}


  public UUID save(UserDataDto userDto) {

    // fetch and convert values for the user values
    // TODO(Marvin): 18.10.2022 Implement and convert values from DTO to Entity
    var user = UserData.builder()
                       .id(UUID.randomUUID())
                       .age(25)
                       .sex(Sex.FEMALE.getValue())
                       .familyStatus(FamiliyStatus.DIVORCED.getKey())
                       .salary(SaleryCategory.TWENTY_TO_THIRTY_K.getKey())
                       .educationLevel(EducationLevel.SECONDARY_SCHOOL.getKey())
                       .employmentStatus(EmploymentStatus.EMPLOYEE.getKey())
                       .build();

//    return repository.save(user).getId();
    repository.save(user);
    return UUID.randomUUID();
  }

}
