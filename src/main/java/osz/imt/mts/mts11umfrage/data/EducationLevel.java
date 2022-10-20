package osz.imt.mts.mts11umfrage.data;

/**
 * Enumeration holding data for different education levels..
 *
 * <p>Created by: Jack</p>
 * <p>Date: 14.10.2022</p>
 */
public enum EducationLevel {
  NONE("Ohne", 0),
  SECONDARY_SCHOOL("Hauptschule", 1),
  MITTLERE_REIFE("Mittlere Reife/ Realschulabschluss", 2),
  ALEVEL("Hochschulreife", 3),
  AFTER_SCHOOL_GRAD("Nachschulischer Abschluss", 4),
  APPRENTICESHIP("Ausbildung", 5),
  STUDY("Studium", 6);

  /**
   * The name of the level assigned to the option.
   */
  private final String value;
  /**
   * The Key associated with the option.
   */
  private final int key;

  /**
   * Return the String value assigned with the option selected.
   *
   * @return name of the selection.
   */
  public String getValue() {

    return value;
  }

  public int getKey() {

    return key;
  }

  /**
   * Return the value assigned to the current option.
   *
   * @param level the level
   * @param key
   */
  EducationLevel(String level, int key) {

    this.value = level;
    this.key = key;
  }

}
