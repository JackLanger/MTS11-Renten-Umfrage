package osz.imt.mts.mts11umfrage.data;

/**
 * Enum to retrive the String value of the employmentstatus.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 14.10.2022</p>
 */
public enum EmploymentStatus {
  UNEMPLOYED("Ohne", 0),
  EMPLOYEE("Angestellt", 1),
  SELF_EMPLOYED("Selbstständig", 2),
  PUBLIC_SERVICE("Beamted", 3),
  ;

  /**
   * Returns the status assigned to the Option selected.
   */
  private final String value;
  /**
   * The integer key associated with the option.
   */
  private final int key;

  /**
   * Returns the key of the option.
   *
   * @return the key
   */
  public int getKey() {

    return key;
  }

  public String getValue() {

    return value;
  }

  /**
   * Creates a new Option for the Employeestatus.
   *
   * @param status the string value associated with the status.
   */
  EmploymentStatus(String status, int key) {

    this.value = status;
    this.key = key;
  }
}
