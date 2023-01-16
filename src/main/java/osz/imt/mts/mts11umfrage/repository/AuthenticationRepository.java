package osz.imt.mts.mts11umfrage.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import osz.imt.mts.mts11umfrage.models.TokenAuthentification;

/**
 * Authentication Repository. Responsible for retriving and saving data to and from the respective
 * table.
 */
public interface AuthenticationRepository extends JpaRepository<TokenAuthentification, String> {

  /**
   * Selects the entry where the hash is equal to the argument provided.
   *
   * @param hash the hash to look for
   * @return entry where the hash match, if no entry is found null.
   */
  @Query("SELECT t FROM TokenAuthentification t WHERE t.hash = ?1")
  TokenAuthentification findByHash(String hash);

  /**
   * Selects only the entries where hash and salt match.
   *
   * @param hash the hash
   * @param salt the salt
   * @return entry where hash and salt match, if no match is found null is return
   */
  @Query("SELECT t FROM TokenAuthentification t WHERE t.hash = ?1 AND t.salt = ?2")
  TokenAuthentification findByHashAndSalt(String hash, String salt);

  /**
   * Selects all salts and returns a set of salts.
   *
   * @return all registered salts as {@link Set}.
   */
  @Query("SELECT t.salt FROM TokenAuthentification t")
  Set<String> getAllSalts();

}
