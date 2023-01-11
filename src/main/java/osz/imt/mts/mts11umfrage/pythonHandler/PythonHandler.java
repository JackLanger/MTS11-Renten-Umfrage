package osz.imt.mts.mts11umfrage.pythonHandler;


import static osz.imt.mts.mts11umfrage.utils.PathConstants.CSV_PY;
import static osz.imt.mts.mts11umfrage.utils.PathConstants.DATA_JSON_PATH_CACHE;
import static osz.imt.mts.mts11umfrage.utils.PathConstants.EXCEL_PY;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.service.EvaluationService;

/**
 * Service to Handle Python tasks in Java. Basically a wrapper around a python script.
 *
 * @author Moritz Hartmann
 */
@Service
public class PythonHandler {

  /**
   * Evaluation Service to be autowired.
   */
  private final transient EvaluationService evaluationService;

  /**
   * Creates and autowires a new PythonHandler object.
   *
   * @param evaluationService the evaluation service.
   */
  @Autowired
  public PythonHandler(EvaluationService evaluationService) {

    this.evaluationService = evaluationService;
  }

  /**
   * Creates a new File with the given content and saves it at the provided path.
   *
   * @param content The contents of the File
   * @param path    the path where to save the file
   * @throws IOException If path is unreachable
   */
  public static void create_file(String content, String path) throws IOException {

    Files.writeString(
        Paths.get(path).toAbsolutePath(),
        content);
  }


  /**
   * Executes the python script.
   *
   * @param type The type of file to be generated
   */
  public void runScript(String type) {  // todo: replace with enum.

    String command = "python3 ";
    switch (type) {
      case "csv" -> command += CSV_PY;
      case "xlsx" -> command += EXCEL_PY;
      default -> throw new IllegalStateException("Unexpected value: " + type);
    }

    Gson gson = new Gson();

    String json = gson.toJson(evaluationService.findAll());
    try {
      create_file(json, DATA_JSON_PATH_CACHE);
    } catch (IOException e) {
      e.printStackTrace();
    }


    Process process = null;


    try {
      ProcessBuilder builder = new ProcessBuilder();

      builder.command("bash", "-c",
                      command); //
                                        /*+
                                        " "
                                        +"-downloadpath " +
                                        "/bin/venv/media/python/"+FILENAME+".xlsx"
                                        +" -datapath " + "/bin/venv/media/python/cache/data.json"*/


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
