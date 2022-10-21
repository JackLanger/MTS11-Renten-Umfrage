package osz.imt.mts.mts11umfrage.utils;

import java.util.Objects;
import org.springframework.lang.Nullable;

/**
 * .
 *
 * <p>Created by: Jack</p>
 * <p>Date: 21.10.2022</p>
 */
public class ValidationUtils {

  public static final String EMPTY_STRING = "";

  /**
   * Checks if a string is blank.
   *
   * @param s the string
   * @return true if is blank
   */
  public static boolean isBlank(String s) {

    return s.equals(EMPTY_STRING);
  }

  /**
   * Checks if a string is not blank.
   *
   * @param s the string
   * @return true if is blank not
   */
  public static boolean isNotBlank(String s) {

    return !s.equals(EMPTY_STRING);
  }

  /**
   * checks weather a string is null or blank.
   *
   * @param s string to check
   * @return true if null or blank
   */
  public static boolean isBlankOrNull(@Nullable String s) {

    return Objects.isNull(s) || isBlank(s);
  }

}
