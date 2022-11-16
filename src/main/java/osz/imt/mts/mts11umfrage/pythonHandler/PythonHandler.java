package osz.imt.mts.mts11umfrage.pythonHandler;

import static osz.imt.mts.mts11umfrage.utils.PathUtils.DOWNLOAD_PATH;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class PythonHandler {
  private final String os = System.getProperty("os.name");
  private String MAIN_PY="";

  public PythonHandler() {
    if (this.os.contains("windows")) {
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
    String python_file_path = Paths.get("").toAbsolutePath().toString()+MAIN_PY;

    String python_venv_path = Paths.get("").toAbsolutePath().toString()+ SCRIPTS;

    //output Directory
    String outputDir = Paths.get("").toAbsolutePath().toString()+OUTPUT;

    System.out.println("Python file path: " + python_file_path);
    System.out.println("Python venv path: " + python_venv_path);
    System.out.println("Output Directory: " + outputDir);
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
                        python_venv_path + " && " + command+ " " +DOWNLOAD_PATH);
      } else {
        builder.command("bash", "-c",
                        python_venv_path + " && " + command + " "+
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
