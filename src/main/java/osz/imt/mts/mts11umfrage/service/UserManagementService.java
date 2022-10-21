package osz.imt.mts.mts11umfrage.service;

import java.util.UUID;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * UserData management service to collect user_data session id and store in memory while user_data
 * not yet finished with the survey. Further should manage data in a in memory store e.g. Redis and
 * verify that a given user has already finished or left the survey. if user left a survey they
 * should pick up where they left, therefore the index of the answer should be provided back to the
 * browser.
 */
@Service
public class UserManagementService {
  // TODO(Moritz): 16.10.2022 implement
  
  @Cacheable("UUID")
  public UUID getUserId() {
    // TODO(...): 16.10.2022 Implement
    return UUID.randomUUID();
  }

  public void store(UUID id) {
    // TODO(...): 16.10.2022 implement
  }

}
