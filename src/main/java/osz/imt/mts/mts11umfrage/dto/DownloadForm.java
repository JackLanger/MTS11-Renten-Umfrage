package osz.imt.mts.mts11umfrage.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Download Form object to provide functionality for download of multiple questions in a single
 * response.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 12.12.2022</p>
 *
 * @author Jacek Langer
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DownloadForm {

  /**
   * Indizes to download.
   */
  List<Integer> indizes;
  /**
   * Token.
   */
  String secretToken;

  /**
   * Adds a new index to the list of indices
   *
   * @param i the index of the question to retrieve.
   */
  public void add(int i) {

    if (indizes == null) {
      this.indizes = new ArrayList<>();
    }
    this.indizes.add(i);
  }

}
