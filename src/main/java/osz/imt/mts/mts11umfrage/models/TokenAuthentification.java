package osz.imt.mts.mts11umfrage.models;

import lombok.*;
import osz.imt.mts.mts11umfrage.dto.QuestionAnswerDto;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osz.imt.mts.mts11umfrage.dto.TokenDto;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_token_authentification")
public class TokenAuthentification implements DtoTransorm<TokenDto>{

    @Id
    private String hash;

    private String salt;

    @Override
    public TokenDto toDto() {
        return null;
    }
}
