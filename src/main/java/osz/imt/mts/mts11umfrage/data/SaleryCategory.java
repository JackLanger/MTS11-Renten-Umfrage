package osz.imt.mts.mts11umfrage.data;

/**
 * .
 *
 * <p>Created by: Jack</p>
 * <p>Date: 14.10.2022</p>
 */
public enum SaleryCategory {
  NONE("Keins", 0),
  LESS_THAN_10K("bis 10.000 €", 1),
  TEN_TO_TWENTY_K("10.001 - 20.000 €", 2),
  TWENTY_TO_THIRTY_K("20.001 - 30.000 €", 3),
  THIRTY_TO_50_K("30.001 - 50.000 €", 4),
  MORE_THAN_50_K("mehr als 50.000 €", 5);

  /**
   * String value of the option.
   */
  private final String value;
  /**
   * Key of the option.
   */
  private final int key;

  /**
   * Returns the key of the selected option.
   *
   * @return the key.
   */
  public int getKey() {

    return key;
  }

  /**
   * returns the String assigned to the selected option.
   *
   * @return
   */
  public String getValue() {

    return value;
  }


  /**
   * Creates a new SaleryCategory.
   *
   * @param saleryCategory the string value for the category
   * @param key            the key for the category
   */
  SaleryCategory(String saleryCategory, int key) {

    this.value = saleryCategory;
    this.key = key;
  }
}
