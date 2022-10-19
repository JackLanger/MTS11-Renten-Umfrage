package osz.imt.mts.mts11umfrage.utils;

import java.time.LocalDate;

/**
 * .
 *
 * <p>Created by: Jack</p>
 * <p>Date: 19.10.2022</p>
 */
public class DateUtils {
// TODO(Jack): 19.10.2022 Implement.

  public static int getAge(LocalDate date) {

    int untilBirthday = LocalDate.now().getDayOfYear() - date.getDayOfYear();

    var age = LocalDate.now().getYear() - date.getYear();

    if (untilBirthday < 0)
      age--;

    return age;
  }

}
