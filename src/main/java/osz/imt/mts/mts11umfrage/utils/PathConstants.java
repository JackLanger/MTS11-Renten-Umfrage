package osz.imt.mts.mts11umfrage.utils;

import java.nio.file.Paths;

/**
 * Path constants class. Paths used in the Application are set and managed centrally in this class.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 15.11.2022</p>
 *
 * @author Jacek Langer
 */
public final class PathConstants {

  /**
   * File name for json file.
   */
  public final static String FILENAME_JSON = "Data.json";
  /**
   * File name for xlsx file.
   */
  public final static String FILENAME_XLSX = "Data.xlsx";
  /**
   * File name for csv file.
   */
  public final static String FILENAME_CSV = "Data.csv";
  /**
   * Path to /bin/venv/media/python.
   */
  public final static String DOWNLOAD_PATH = Paths.get("/bin/venv/media/python").toAbsolutePath()
                                                  .toString();
  /**
   * Path to XLSX File.
   */
  public final static String XLSX_DOWNLOAD_PATH = Paths.get(DOWNLOAD_PATH, FILENAME_XLSX)
                                                       .toAbsolutePath().toString();
  /**
   * Path to JSON File.
   */
  public final static String JSON_DOWNLOAD_PATH = Paths.get(DOWNLOAD_PATH, FILENAME_JSON)
                                                       .toAbsolutePath().toString();
  /**
   * Path to CSV File.
   */
  public final static String CSV_DOWNLOAD_PATH = Paths.get(DOWNLOAD_PATH, FILENAME_CSV)
                                                      .toAbsolutePath().toString();
  /**
   * Path to data.json.
   */
  public final static String DATA_JSON_PATH_CACHE = "/bin/venv/media/python/cache/data.json";
  /**
   * Path to main.py.
   */
  public final static String EXCEL_PY = "/bin/venv/main.py";
  /**
   * Path to csv_writer.py.
   */
  public final static String CSV_PY = "/bin/venv/csv_writer.py";

  /**
   * Private Constructor as no init allowed.
   */
  private PathConstants() {
    // empty no init
  }

}
