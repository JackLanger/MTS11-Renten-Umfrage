package osz.imt.mts.mts11umfrage.controller;

import static osz.imt.mts.mts11umfrage.utils.PathUtils.DOWNLOAD_PATH;
import static osz.imt.mts.mts11umfrage.utils.PathUtils.FILENAME;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import osz.imt.mts.mts11umfrage.pythonHandler.PythonHandler;
import osz.imt.mts.mts11umfrage.service.AuthService;

/**
 * .
 *
 * <p>Created by: Jack</p>
 * <p>Date: 16.11.2022</p>
 */
@Controller
public class DownloadController {


  //TODO(Moritz): refactor methods should not return void but a ResponseEntity<Resource>
  // otherwise you need to set the response code.
  private static final String JSON = "application/json";

  private final AuthService authService;
  private final PythonHandler pythonHandler;

  @Autowired
    public DownloadController(AuthService authService, PythonHandler pythonHandler) {
        this.authService = authService;
        this.pythonHandler = pythonHandler;
    }


    HttpHeaders getHeaders(String filename) {

    HttpHeaders headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");
    headers.add("Content-Disposition", "attachment; filename=" + filename);

    return headers;
  }

  @RequestMapping(value = "/download/xlsx", method = RequestMethod.POST)
  public ResponseEntity<InputStreamResource> downloadExcel(@RequestParam String token,
                                                           HttpServletResponse response)
      throws IOException, NoSuchAlgorithmException {

        if (authService.verifyToken(token)) {

            PythonHandler pythonHandler = new PythonHandler();
            pythonHandler.runScript();
            File file = new File(DOWNLOAD_PATH + "\\" + FILENAME + ".xlsx");
            InputStream inputStream = new FileInputStream(file);
            InputStreamResource resource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    .headers(getHeaders(file.getName()))
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        return ResponseEntity.status(401).build();
    }

    /**
     * todo(Moritz): javadoc.
     *
     * @param response
     * @return
     * @throws IOException
     */

    @RequestMapping(value = "/download/json", method = RequestMethod.POST)
    public ResponseEntity<InputStreamResource> downloadJson(@RequestParam String token, HttpServletResponse response)
            throws IOException, NoSuchAlgorithmException {

        if (authService.verifyToken(token)) {

            PythonHandler pythonHandler = new PythonHandler();
            pythonHandler.runScript();
            File file = new File(DOWNLOAD_PATH + "\\" + FILENAME + ".json");
            InputStream inputStream = new FileInputStream(file);

            InputStreamResource resource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    .headers(getHeaders(file.getName()))
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        return ResponseEntity.status(401).build();
    }


}
