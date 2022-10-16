package osz.imt.mts.mts11umfrage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osz.imt.mts.mts11umfrage.utils.EducationLevel;
import osz.imt.mts.mts11umfrage.utils.EmploymentStatus;
import osz.imt.mts.mts11umfrage.utils.SaleryCategory;

/**
 * Dto Class for a User.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 14.10.2022</p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private SaleryCategory salery;
  private int familyStatus;
  private EducationLevel educationLevel;
  private EmploymentStatus employmentStatus;
  private String sex;
  private int age = 25;

  public void setSalery(String salery) {

    var val = Integer.valueOf(salery);

    if (val == 0) {
      this.salery = SaleryCategory.NONE;

    }

    if (val < 50000) {
      if (val < 30000) {
        if (val < 20000) {
          if (val < 10000) {
            this.salery = SaleryCategory.LESS_THAN_10K;
          } else {
            this.salery = SaleryCategory.TEN_TO_TWENTY_K;
          }
        } else {
          this.salery = SaleryCategory.TWENTY_TO_THIRTY_K;
        }
      } else {
        this.salery = SaleryCategory.THIRTY_TO_50_K;
      }
    } else {
      this.salery = SaleryCategory.MORE_THAN_50_K;
    }

  }

}
