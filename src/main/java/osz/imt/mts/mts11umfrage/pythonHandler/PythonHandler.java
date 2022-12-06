package osz.imt.mts.mts11umfrage.pythonHandler;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.service.EvaluationService;

import static osz.imt.mts.mts11umfrage.utils.OsInformation.OS;
import static osz.imt.mts.mts11umfrage.utils.PathUtils.*;
import static osz.imt.mts.mts11umfrage.utils.PathUtils.DATA_JSON_PATH_CACHE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class PythonHandler{
  @Autowired
  private EvaluationService evaluationService;


  public static void create_file(String content, String path) throws IOException {
    Files.writeString(
            Paths.get(path).toAbsolutePath(),
            content);
  }

  public PythonHandler() {
  }

  public void runScript() {
    //TODO: Hier muss dto übergeben werden
    Gson gson = new Gson();

    String json = gson.toJson(evaluationService.findAll());
    try{
      create_file(json,DATA_JSON_PATH_CACHE);
    }
    catch (IOException e) {
      e.printStackTrace();
    }


    Process process = null;


    // Python script file path
    String python_file_path = OS.contains("Windows") ? Paths.get("").toAbsolutePath() + MAIN_PY: MAIN_PY;


    String python_venv_path = OS.contains("Windows") ? Paths.get("").toAbsolutePath() + SCRIPTS: SCRIPTS;


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
        builder.command("bash", "-c",
                                        command
                                        /*+
                                        " "
                                        +"-downloadpath " +
                                        "/bin/venv/media/python/"+FILENAME+".xlsx"
                                        +" -datapath " + "/bin/venv/media/python/cache/data.json"*/);

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
