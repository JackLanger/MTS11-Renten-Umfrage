package osz.imt.mts.mts11umfrage.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * .
 *
 * <p>Created by: Jack</p>
 * <p>Date: 12.12.2022</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DownloadForm {

  List<Integer> indizes;
  String secretToken;

  public void add(Integer i) {

    if (indizes == null) {
      this.indizes = new ArrayList<>();
    }
    this.indizes.add(i);
  }

}
