package osz.imt.mts.mts11umfrage.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import osz.imt.mts.mts11umfrage.models.TokenAuthentification;

import java.util.List;

public interface AuthenticationRepository extends JpaRepository<TokenAuthentification, String> {
@Query("SELECT t FROM TokenAuthentification t WHERE t.hash = ?1")
TokenAuthentification findByHash(String hash);


@Query("SELECT t FROM TokenAuthentification t WHERE t.hash = ?1 AND t.salt = ?2")
TokenAuthentification findByHashAndSalt(String hash, String salt);

@Query("SELECT t.salt FROM TokenAuthentification t")
List<String> getAllSalts();
}
