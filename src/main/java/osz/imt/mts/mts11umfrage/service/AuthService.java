package osz.imt.mts.mts11umfrage.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.repository.AuthenticationRepository;

/**
 * Authentication Service. Responsible for authentication tasks.
 */
@Service
public class AuthService {

  /**
   * Authentication Repository to handle actions against the authentication table.
   */
  private transient final AuthenticationRepository authenticationRepository;

  /**
   * Creates a new {@link AuthService} object and autowires the repository needed.
   *
   * @param authenticationRepository the authentication repository.
   */
  @Autowired
  public AuthService(AuthenticationRepository authenticationRepository) {

    this.authenticationRepository = authenticationRepository;
  }

  /**
   * Verifies if the token provided is a valid and registered token.
   *
   * @param Token The token to verify
   * @return true if token valid
   * @throws NoSuchAlgorithmException if hashing not possible with the provided algorithm
   */
  public boolean verifyToken(String Token) throws NoSuchAlgorithmException {
    // todo: refactor. As no token is provided as argument all NoSuchAlgorithm exceptions should
    //  be handled within the method rather than passed through to the next level.
    MessageDigest digest = MessageDigest.getInstance("SHA-256");


    Set<String> salts = authenticationRepository.getAllSalts();
    for (String salt : salts) {
      var hashAndSalt = digest.digest((Token + salt).getBytes(StandardCharsets.UTF_8));
      if (authenticationRepository.findByHashAndSalt(toHexString(hashAndSalt), salt) != null) {
        return true;
      }
    }
    return false;
  }

  /**
   * Converts a byte array to a Hex string.
   *
   * @param bytes bytes to convert
   * @return the hex representation of the byte array.
   */
  public static String toHexString(byte[] bytes) {

    StringBuilder hexString = new StringBuilder();

    for (byte aByte : bytes) {
      String hex = Integer.toHexString(0xFF & aByte);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }

    return hexString.toString();
  }

}
