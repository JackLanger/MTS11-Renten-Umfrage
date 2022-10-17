package osz.imt.mts.mts11umfrage.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import osz.imt.mts.mts11umfrage.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
}