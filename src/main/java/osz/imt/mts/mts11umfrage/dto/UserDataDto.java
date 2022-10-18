package osz.imt.mts.mts11umfrage.dto;

import java.time.LocalDate;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osz.imt.mts.mts11umfrage.data.EducationLevel;
import osz.imt.mts.mts11umfrage.data.EmploymentStatus;
import osz.imt.mts.mts11umfrage.data.FamiliyStatus;
import osz.imt.mts.mts11umfrage.data.SaleryCategory;

/**
 * Dto Class for a UserData.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 14.10.2022</p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDto {

  private SaleryCategory salary;
  private FamiliyStatus familyStatus;
  private EducationLevel educationLevel;
  private EmploymentStatus employmentStatus;
  private String sex;
  private LocalDate age;

  public void setSalary(String salary) {

    var sal = Arrays.stream(SaleryCategory.values())
                    .filter(s -> s.getValue().equals(salary))
                    .findFirst();
    sal.ifPresent(s -> this.salary = s);
  }


  public void setFamilyStatus(int familyStatus) {

    var fam = Arrays.stream(FamiliyStatus.values()).filter(f -> f.getKey() == (familyStatus))
                    .findFirst();
    fam.ifPresent(f -> this.familyStatus = f);
  }

  public void setEducationLevel(int educationLevel) {

    var level = Arrays.stream(EducationLevel.values()).filter(
        l -> l.getKey() == (educationLevel)).findFirst();

    level.ifPresent(l -> this.educationLevel = l);
  }

  public void setEmploymentStatus(String employmentStatus) {

    var status = Arrays.stream(EmploymentStatus.values()).filter(
        v -> v.getValue().equals(employmentStatus)).findFirst();
    status.ifPresent(st -> this.employmentStatus = st);
  }

  public void setAge(String age) {

    var ageArr = age.split("-");
    int y, m, d;
    y = m = d = 0;
    for (int i = 0; i < ageArr.length; i++) {
      switch (i) {
        case 0:
          y = Integer.parseInt(ageArr[i]);
        case 1:
          m = Integer.parseInt(ageArr[i]);
        case 2:
          d = Integer.parseInt(ageArr[i]);
      }
    }

    this.age = LocalDate.of(y, m, d);
  }

}
