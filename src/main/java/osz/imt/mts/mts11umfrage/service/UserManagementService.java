package osz.imt.mts.mts11umfrage.service;

import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * User management service to collect user session id and store in memory while user not yet
 * finished with the survey. User id can be stored or fetched via the cookies and or cached in local
 * memory.
 */
@Service
public class UserManagementService {
  // TODO(Moritz): 16.10.2022 implement

  public UUID getUserId() {
    // TODO(...): 16.10.2022 Implement
    return UUID.randomUUID();
  }

  public void store(UUID id) {
    // TODO(...): 16.10.2022 implement
  }

}
