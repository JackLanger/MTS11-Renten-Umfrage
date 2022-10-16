package osz.imt.mts.mts11umfrage.utils;

/**
 * Enum to retrive the String value of the employmentstatus.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 14.10.2022</p>
 */
public enum EmploymentStatus {
  UNEMPLOYED("Ohne"),
  SELF_EMPLOYED("Selbstständig"),
  EMPLOYEE("Angestellt"),
  PUBLIC_SERVICE("Beamted"),
  ;


  /**
   * Returns the status assigned to the Option selected.
   */
  private final String status;

  public String getStatus() {

    return status;
  }

  /**
   * Creates a new Option for the Employeestatus.
   *
   * @param status the string value associated with the status.
   */
  EmploymentStatus(String status) {

    this.status = status;
  }
}
