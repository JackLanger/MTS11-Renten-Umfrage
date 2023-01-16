package osz.imt.mts.mts11umfrage.models;

/**
 * This interface provides a method to convert a Entity to its respective dto.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 16.11.2022</p>
 */
public interface DtoTransorm<DtoT> {

  /**
   * Maps the Values of the Entity to a dto and returns its respective {@link DtoT}.
   *
   * @return {@link DtoT} with mapped values.
   */
  DtoT toDto();

}
