package osz.imt.mts.mts11umfrage.utils;

import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Paths;

import static osz.imt.mts.mts11umfrage.utils.OsInformation.OS;

/**
 * .
 *
 * <p>Created by: Jack</p>
 * <p>Date: 15.11.2022</p>
 */
public final class PathUtils {
    // General-------------------
    public static final String FILENAME = "Data";
    // Linux---------------------
    //@Value("${python.downloads.path}")
    private final static String LINUX_DOWNLOAD_PATH_PRODUCTION ="/bin/venv/media/python"; //"/bin/venv/media/python/";
    private final static String MAIN_PY_LINUX = "/bin/venv/main.py";
    private final static String SCRIPTS_LINUX = "/bin/venv/";

    // Windows-------------------
    private final static String WINDOWS_DOWNLOAD_PATH_DEVELOPMENT = Paths.get("").toAbsolutePath() + "/src/main/resources/media/python";
    private final static String MAIN_PY_WINDOWS = "/DataHandler/main.py";
    private final static String SCRIPTS_WINDOWS = "/DataHandler/venv/Scripts";


    // Set paths based on OS
    public final static String DOWNLOAD_PATH = OS.contains("Windows") ? WINDOWS_DOWNLOAD_PATH_DEVELOPMENT : LINUX_DOWNLOAD_PATH_PRODUCTION;
    public final static String MAIN_PY = OS.contains("Windows") ? MAIN_PY_WINDOWS : MAIN_PY_LINUX;
    public final static String SCRIPTS = OS.contains("Windows") ? SCRIPTS_WINDOWS : SCRIPTS_LINUX;



     public final static String DATA_JSON_PATH_CACHE = DOWNLOAD_PATH + "/cache/data.json";

    private PathUtils() {
    }

}
