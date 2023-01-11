package osz.imt.mts.mts11umfrage.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osz.imt.mts.mts11umfrage.dto.TokenDto;

/**
 * Token Authentication Entity.
 *
 * @author Moritz Hartmann
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_token_authentification")
public class TokenAuthentification implements DtoTransorm<TokenDto> {

    /**
     * The hashed value of the token.
     */
    @Id
    private String hash;
    /**
     * Salt used for hashing.
     */
    private String salt;


    @Override
    public TokenDto toDto() {

        return null;
    }
}
