package osz.imt.mts.mts11umfrage.pythonHandler;

import static osz.imt.mts.mts11umfrage.utils.PathUtils.DOWNLOAD_PATH;
import static osz.imt.mts.mts11umfrage.utils.OsInformation.OS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class PythonHandler {
  private final String os = System.getProperty("os.name");
  private String MAIN_PY="";

  public PythonHandler() {
    if (OS.contains("windows")) {
      this.MAIN_PY = "/DataHandler/main.py";
    } else {
      this.MAIN_PY = "/DataHandler/main.py";
    }
  }

  private final static String SCRIPTS =
      "/DataHandler/venv/Scripts";


  //private final static String MAIN_PY =
  //    "/bin/venv/main.py";

  private final static String OUTPUT = "/src/main/resources/media/python";

  public void runScript() {

    Process process = null;


    // Python script file path
    String python_file_path = Paths.get("").toAbsolutePath() + MAIN_PY;

    String python_venv_path = Paths.get("").toAbsolutePath() + SCRIPTS;

    System.out.println("Python file path: " + python_file_path);
    System.out.println("Python venv path: " + python_venv_path);
    System.out.println("Output Directory: " + DOWNLOAD_PATH);

    if (OS.contains("Windows")) {
      python_venv_path += "/activate.bat";
    } else {
      python_venv_path += "/activate";
      python_venv_path = ". " + python_venv_path;
    }

    //check if os is windows or linux
    String command = "";

    if (OS.contains("Windows")) {
      command = "py " + python_file_path;
    } else {
      command = "python3 " + python_file_path;
    }

    try {
      ProcessBuilder builder = new ProcessBuilder();
      if (OS.contains("Windows")) {
        builder.command("cmd.exe", "/c", python_venv_path
                                        +
                                        " && "
                                        +
                                        command
                                        +
                                        " "
                                        +
                                        DOWNLOAD_PATH);

      } else {
        builder.command("bash", "-c", python_venv_path
                                        +
                                        " && "
                                        +
                                        command
                                        +
                                        " "
                                        +
                                        DOWNLOAD_PATH);

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
