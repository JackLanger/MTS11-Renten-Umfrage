package osz.imt.mts.mts11umfrage.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osz.imt.mts.mts11umfrage.repository.AuthenticationRepository;

@Service
public class AuthService {

  private transient final AuthenticationRepository authenticationRepository;

  @Autowired
  public AuthService(AuthenticationRepository authenticationRepository) {

    this.authenticationRepository = authenticationRepository;
  }

  public boolean verifyToken(String Token) throws NoSuchAlgorithmException {

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
