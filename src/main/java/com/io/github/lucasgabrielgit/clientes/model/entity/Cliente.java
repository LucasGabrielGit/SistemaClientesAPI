package com.io.github.lucasgabrielgit.clientes.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 200)
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String nome;

    @Column(nullable = false, length = 11)
    @NotNull(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    private String cpf;

    @Column(name = "data_cadastro", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy HH:mm",
            timezone = "America/Brazil")
    private Date dataCadastro;

    @Column(name = "email")
    private String email;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "estado_uf")
    private String estado_uf;
    @Column(name = "cep")
    private Integer cep;

    @PrePersist
    public void prePersist() {
        setDataCadastro(new Date());
    }
}
