package osz.imt.mts.mts11umfrage.utils;

/**
 * .
 *
 * <p>Created by: Jack</p>
 * <p>Date: 14.10.2022</p>
 */
public enum SaleryCategory {
  NONE("Keins"),
  LESS_THAN_10K("weniger als 10.000 €"),
  TEN_TO_TWENTY_K("10.000 - 20.000 €"),
  TWENTY_TO_THIRTY_K("20.000 - 30.000 €"),
  THIRTY_TO_50_K("30.000 - 50.000 €"),
  MORE_THAN_50_K("mehr als 50.000 €");


  private final String saleryCategory;

  /**
   * returns the String assigned to the selected option.
   *
   * @return
   */
  public String getSaleryCategory() {

    return saleryCategory;
  }

  SaleryCategory(String saleryCategory) {

    this.saleryCategory = saleryCategory;
  }
}
