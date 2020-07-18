package com.io.github.lucasgabrielgit.clientes.model.repository;

import com.io.github.lucasgabrielgit.clientes.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepository extends JpaRepository<Cliente, Integer> {

}
