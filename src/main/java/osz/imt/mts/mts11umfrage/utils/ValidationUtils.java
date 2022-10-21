package osz.imt.mts.mts11umfrage.utils;

import java.util.Objects;
import org.springframework.lang.Nullable;

/**
 * Validation utility class for string validation.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 21.10.2022</p>
 */
public final class ValidationUtils {

  /**
   * An empty string.
   */
  public static final String EMPTY_STRING = "";

  /**
   * Checks if a string is blank.
   *
   * @param s the string
   * @return true if is blank
   */
  public static boolean isBlank(final String s) {

    return EMPTY_STRING.equals(s);
  }

  /**
   * Checks if a string is not blank.
   *
   * @param s the string
   * @return true if is blank not
   */
  public static boolean isNotBlank(final String s) {

    return !EMPTY_STRING.equals(s);
  }

  /**
   * checks weather a string is null or blank.
   *
   * @param s string to check
   * @return true if null or blank
   */
  public static boolean isBlankOrNull(@Nullable final String s) {

    return Objects.isNull(s) || isBlank(s);
  }

  /**
   * Prevent initialization. Utility class.
   */
  private ValidationUtils() {
    // empty. no init.
  }

}
