package osz.imt.mts.mts11umfrage.models;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "t_users")
public class User {

  // check values if saved in db or as numeric value in order to hardcode and map to enum value.
  @Id
  @Column(name = "p_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;
  private int salary;
  private int familyStatus;
  private int educationLevel;
  private String employmentStatus;
  private String sex;
  private int age;

}
