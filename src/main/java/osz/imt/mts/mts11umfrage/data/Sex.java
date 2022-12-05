package osz.imt.mts.mts11umfrage.data;

/**
 * Sex Enum to map the user sex to a string value.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 17.10.2022</p>
 */
public enum Sex {
  /**
   * Male.
   */
  MALE("männlich", ""),
  /**
   * Female.
   */
  FEMALE("weiblich", ""),
  /**
   * Diverse.
   */
  DIVERSE("diverse", "");

  /**
   * String value of the selected option.
   */
  String value;

  /**
   * Path to icon.
   */
  String imagePath;

  /**
   * Returns the value associated with the option.
   *
   * @return the String value of the option
   */
  public String getValue() {

    return this.value;
  }

  /**
   * Returns the path to the icon.
   *
   * @return String contianing the path to th icon.
   */
  public String getImagePath() {

    return this.imagePath;
  }

  /**
   * Creates a new Sex value.
   *
   * @param value the String value associated with the option.
   */
  Sex(final String value, final String icon) {

    this.value = value;
    this.imagePath = icon;
  }
}
