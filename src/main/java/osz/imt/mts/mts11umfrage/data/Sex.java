package osz.imt.mts.mts11umfrage.data;

/**
 * Sex Enum to map the user sex to a string value.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 17.10.2022</p>
 */
public enum Sex {
  MALE("männlich"),
  FEMALE("weiblich"),
  DIVERSE("diverse");

  /**
   * String value of the selected option.
   */
  String value;

  /**
   * Returns the value associated with the option.
   *
   * @return the String value of the option
   */
  public String getValue() {

    return value;
  }

  /**
   * Creates a new Sex value.
   *
   * @param value the String value associated with the option.
   */
  Sex(String value) {

    this.value = value;
  }
}
