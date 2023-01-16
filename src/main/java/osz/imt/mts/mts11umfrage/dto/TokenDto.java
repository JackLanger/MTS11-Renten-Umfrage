package osz.imt.mts.mts11umfrage.dto;

/**
 * Dto Class for a Token used for authentication.
 *
 * @param hash The hashed value
 * @param salt Salt for hashing
 * @author Moritz Hartmann
 */
public record TokenDto(String hash, String salt) {
}

