package osz.imt.mts.mts11umfrage.utils;

/**
 * Enumeration holding data for different education levels..
 *
 * <p>Created by: Jack</p>
 * <p>Date: 14.10.2022</p>
 */
public enum EducationLevel {
  NONE("Ohne"),
  APPRENTICESHIP("Ausbildung"),
  MASTER_TECHNOLOGY("Meister / Techniker"),
  STUDY(""),
  ;

  /**
   * The name of the level assigned to the option.
   */
  private final String level;

  /**
   * Return the String value assigned with the option selected.
   *
   * @return name of the selection.
   */
  public String getLevel() {

    return level;
  }

  /**
   * Return the value assigned to the current option.
   *
   * @param level the level
   */
  EducationLevel(String level) {

    this.level = level;
  }

}
