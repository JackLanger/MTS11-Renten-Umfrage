package osz.imt.mts.mts11umfrage.pythonHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PythonHandler {
  public PythonHandler(){

  }


  public void runScript() {
    Process process = null;
    String os = System.getProperty("os.name");

    // Python script file path
    String python_file_path =  "G:\\pythonProject1\\main.py"; //String sourceDir = System.getProperty("user.dir") + "\\src";
    // venv path
    String python_venv_path = "G:\\pythonProject1\\venv\\Scripts";
    //output Directory
    String outputDir = "G:\\MTS11-Renten-Umfrage\\src\\main\\resources\\files";

    String outputDir_command = "cd " + outputDir;

    if(os.contains("Windows")){
      python_venv_path += "\\activate.bat";

    }else{
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
      if(os.contains("Windows")){
        builder.command("cmd.exe", "/c", python_venv_path + " && " + command + " && " + outputDir_command);
      }else{
        builder.command("bash", "-c", python_venv_path + " && " + command + " && " + outputDir_command);
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
