package osz.imt.mts.mts11umfrage.pythonHandler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.dto.EvaluationDto;
import osz.imt.mts.mts11umfrage.dto.UserAnswerDto;
import osz.imt.mts.mts11umfrage.models.UserAnswer;
import osz.imt.mts.mts11umfrage.repository.UserAnswersRepository;
import osz.imt.mts.mts11umfrage.utils.JsonResponse;
import osz.imt.mts.mts11umfrage.utils.PathUtils;

import static osz.imt.mts.mts11umfrage.utils.OsInformation.OS;
import static osz.imt.mts.mts11umfrage.utils.PathUtils.*;
import static osz.imt.mts.mts11umfrage.utils.PathUtils.DATA_JSON_PATH_CACHE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.charset.StandardCharsets.UTF_8;

public class PythonHandler{

  public static void create_file(String content, String path) throws IOException {
    Files.writeString(
            Paths.get(path).toAbsolutePath(),
            content);
  }

  public PythonHandler() {
  }

  public void runScript() {
    //TODO: Hier muss dto übergeben werden
    try{
      create_file(headerJson().toString(),DATA_JSON_PATH_CACHE);
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
                                        +
                                        " "
                                        +"-dp " +
                                        DOWNLOAD_PATH+"//"+FILENAME+".xlsx"
                                        +" -hcp " +
                                        HEADER_JSON_PATH_CACHE
                                        +" -dcp " + DATA_JSON_PATH_CACHE);

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
