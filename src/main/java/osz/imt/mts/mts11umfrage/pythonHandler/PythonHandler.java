package osz.imt.mts.mts11umfrage.pythonHandler;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.service.EvaluationService;

import static osz.imt.mts.mts11umfrage.utils.OsInformation.OS;
import static osz.imt.mts.mts11umfrage.utils.PathUtils.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class PythonHandler {
    @Autowired
    private EvaluationService evaluationService;


    public static void create_file(String content, String path) throws IOException {
        Files.writeString(
                Paths.get(path).toAbsolutePath(),
                content);
    }

    public PythonHandler() {
    }


    public void runScript(String type) {
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
