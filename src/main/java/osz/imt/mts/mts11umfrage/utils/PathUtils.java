package osz.imt.mts.mts11umfrage.utils;

import static osz.imt.mts.mts11umfrage.utils.OsInformation.OS;

import java.nio.file.Paths;

/**
 * .
 *
 * <p>Created by: Jack</p>
 * <p>Date: 15.11.2022</p>
 */
public final class PathUtils {

  private final static String WINDOWS_DOWNLOAD_PATH_DEVELOPMENT = Paths.get("").toAbsolutePath() +
      "/src/main/resources/media/python";
  //    @Value("${python.downloads.path}")
  private final static String LINUX_DOWNLOAD_PATH_PRODUCTION = "/bin/venv/media/python/";

  public final static String DOWNLOAD_PATH = OS.contains("Windows") ?
                                             WINDOWS_DOWNLOAD_PATH_DEVELOPMENT :
                                             LINUX_DOWNLOAD_PATH_PRODUCTION;

  private PathUtils() {
    //
  }

}
