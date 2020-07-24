package com.io.github.lucasgabrielgit.clientes.model.repository;

import com.io.github.lucasgabrielgit.clientes.model.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    @Query("select s from Servico s join s.cliente " +
            "c where upper(c.nome) like upper(:nome)" +
            "and MONTH(s.data) =:mes")
    List<Servico> findByNomeClienteAndMes(@Param("nome") String nome
            , @Param("mes") String mes);
}
