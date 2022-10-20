package osz.imt.mts.mts11umfrage.data;

/**
 * Family status levels.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 17.10.2022</p>
 */
public enum FamiliyStatus {
  SINGLE("Ledig", 0),
  MERRIED("Verheiratet", 1),
  PARTNERSHIP("Eingetragene Lebenspartnerschaft", 2),
  DIVORCED("Geschieden", 3),
  WIDOWD("Verwitwet", 4);

  private final String value;
  private final int key;

  public String getValue() {

    return value;
  }

  public int getKey() {

    return key;
  }

  FamiliyStatus(String value, int key) {

    this.value = value;
    this.key = key;
  }

}
