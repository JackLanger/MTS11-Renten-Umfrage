package osz.imt.mts.mts11umfrage.models;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "t_user_data", catalog = "umfrage", schema = "dbo")
public class UserData {

  // check values if saved in db or as numeric value in order to hardcode and map to enum value.
  @Id
  @Column(name = "id", nullable = false, unique = true, updatable = false)
  @ColumnDefault("NEWID()")
  @GeneratedValue
  private UUID id;
  private int familyStatus;
  private int educationLevel;
  private int employmentStatus;
  private String sex;
  private int age;
  private int salary;

}
