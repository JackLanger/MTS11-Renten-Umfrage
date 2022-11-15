package osz.imt.mts.mts11umfrage.pythonHandler;

import static osz.imt.mts.mts11umfrage.utils.PathUtils.DOWNLOAD_PATH;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;

public class PythonHandler {

  public PythonHandler() {
    // empty
  }

  private final static String SCRIPTS =
      "/src/main/java/osz/imt/mts/mts11umfrage/pythonHandler/DataHandler/venv/Scripts";
  private final static String MAIN_PY =
      "/bin/venv/main.py";
  private final static String OUTPUT = "/src/main/resources/media/python";

  public void runScript() {

    Process process = null;
    String os = System.getProperty("os.name");

    // Python script file path
    String python_file_path = FileSystems.getDefault()
                                         .getPath(MAIN_PY)
                                         .toAbsolutePath()
                                         .toString();

    String python_venv_path = FileSystems.getDefault()
                                         .getPath(SCRIPTS)
                                         .toAbsolutePath()
                                         .toString();
    //output Directory
    String outputDir = FileSystems.getDefault()
                                  .getPath(OUTPUT)
                                  .toAbsolutePath()
                                  .toString();


    String outputDir_command = "cd " + outputDir;

    if (os.contains("Windows")) {
      python_venv_path += "/activate.bat";

    } else {
      python_venv_path += "/activate";
      python_venv_path = ". " + python_venv_path;
    }

    //check if os is windows or linux
    String command = "";
    if (os.contains("Windows")) {
      command = "py " + python_file_path;
    } else {
      command = "python3 " + python_file_path;
    }
    try {
      ProcessBuilder builder = new ProcessBuilder();
      if (os.contains("Windows")) {
        builder.command("cmd.exe", "/c",
                        python_venv_path + " && " + command);//+ " && " + outputDir_command);
      } else {
        builder.command("bash", "-c",
                        python_venv_path + " && " + command +
                            DOWNLOAD_PATH);//+ " && " + outputDir_command);
      }
      builder.redirectErrorStream(true);
      process = builder.start();

      // Read the output
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
