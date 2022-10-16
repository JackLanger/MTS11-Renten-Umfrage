package osz.imt.mts.mts11umfrage.utils;

/**
 * Question Type Enumeration.
 *
 * <p>Created by: Jack</p>
 * <p>Date: 10.10.2022</p>
 */
public enum QuestionTypes {
  /**
   * Boolean value answer.
   */
  Boolean(1),
  /**
   * A scale needs to be provided.
   */
  SCALE(2),
  /**
   * Single answer is possible.
   */
  SINGLEANSWER(3),
  /**
   * A Multiple choice question. Multiple answers can be selected.
   */
  MULTIPLECHOICE(4),
  /**
   * An Input question answer.
   */
  INPUT(5);

  /**
   * The Type of the question.
   */
  private final int type;

  /**
   * Returns the type.
   *
   * @return the type value
   */
  public int getType() {

    return type;
  }

  /**
   * Creates a new Question Type
   *
   * @param i the type id
   */
  QuestionTypes(int i) {

    this.type = i;

  }
}
