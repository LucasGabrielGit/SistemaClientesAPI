package com.io.github.lucasgabrielgit.clientes.rest;

import com.io.github.lucasgabrielgit.clientes.model.entity.Cliente;
import com.io.github.lucasgabrielgit.clientes.model.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("http://localhost:4200")
public class ClienteController {

    private final ClientesRepository repository;

    @Autowired
    public ClienteController(ClientesRepository repository) {

        this.repository = repository;
    }

    //    Salva o cliente no banco de dados
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar(@RequestBody @Valid Cliente cliente) {
        return repository.save(cliente);
    }

    //    Realiza uma pesquisa no banco de dados através do id passado como parâmetro pelo usuário
    @GetMapping("{id}")
    public Cliente acharPorId(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"
                ));
    }

    @GetMapping
    public List<Cliente> obterTudo() {
        return repository.findAll();
    }


    //    Verifica se o cliente requisitado pelo usuário está cadastrado no banco de dados e,
    //    se estiver, o deleta, senão, o apaga
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        repository.findById(id)
                .map(cliente -> {
                    repository.delete(cliente);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    //    Atualiza os dados do cliente caso ele já esteja cadastrado
    @PutMapping("{id}") //Utilizado para atualizar completamente um recurso do banco de dados
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer id, @RequestBody Cliente clienteAtualizado) {
        repository.findById(id)
                .map(cliente -> {
                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setCpf(clienteAtualizado.getCpf());
                    return repository.save(clienteAtualizado);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }
}
