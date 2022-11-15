package osz.imt.mts.mts11umfrage.pythonHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class PythonHandler {

  public PythonHandler() {
    // empty
  }


  public void runScript() {

    Process process = null;
    String os = System.getProperty("os.name");

    // Python script file path
    String python_file_path  = FileSystems.getDefault().getPath("/src/main/java/osz/imt/mts/mts11umfrage/pythonHandler/DataHandler/main.py").toAbsolutePath().toString();
        //"G:\\pythonProject1\\main.py";
    // "\\src";
    // venv path
    String python_venv_path = FileSystems.getDefault().getPath("/src/main/java/osz/imt/mts/mts11umfrage/pythonHandler/DataHandler/venv/Scripts").toAbsolutePath().toString();//"G:\\pythonProject1\\venv\\Scripts";
    //output Directory
    String outputDir = FileSystems.getDefault().getPath("/src/main\\resources\\media\\python").toAbsolutePath().toString();//"G:\\MTS11-Renten-Umfrage\\src\\main\\resources\\files";

    String outputDir_command = "cd " + outputDir;

    if (os.contains("Windows")) {
      python_venv_path += "\\activate.bat";

    } else {
      python_venv_path += "\\activate";
      python_venv_path = "source " + python_venv_path;
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
                        python_venv_path + " && " + command );//+ " && " + outputDir_command);
      } else {
        builder.command("bash", "-c",
                        python_venv_path + " && " + command );//+ " && " + outputDir_command);
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
