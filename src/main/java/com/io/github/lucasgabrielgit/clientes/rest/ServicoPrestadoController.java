package com.io.github.lucasgabrielgit.clientes.rest;

import com.io.github.lucasgabrielgit.clientes.model.entity.Cliente;
import com.io.github.lucasgabrielgit.clientes.model.entity.Servico;
import com.io.github.lucasgabrielgit.clientes.model.repository.ClientesRepository;
import com.io.github.lucasgabrielgit.clientes.model.repository.ServicoRepository;
import com.io.github.lucasgabrielgit.clientes.rest.dto.ServicoDTO;
import com.io.github.lucasgabrielgit.clientes.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/servicos")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class ServicoPrestadoController {

    private final ClientesRepository clientesRepository;
    private final ServicoRepository servicoRepository;
    private final BigDecimalConverter bigDecimalConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Servico salvar(@RequestBody ServicoDTO dto) {
        LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Servico servico = new Servico();
        Integer idCliente = dto.getIdCliente();

        Cliente cliente = clientesRepository.findById(idCliente)
                .orElseThrow(() ->
                        new ResponseStatusException
                                (HttpStatus.BAD_REQUEST, "Cliente inexistente")
                );
        servico.setDescricao(dto.getDescricao());
        servico.setData(data);
        servico.setCliente(cliente);
        servico.setValor(bigDecimalConverter.converter(dto.getPreco()));
        return servicoRepository.save(servico);
    }

    @GetMapping
    public List<Servico> pesquisar(
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(value = "mes", required = false) Integer mes
    ) {
        return servicoRepository.findByNomeClienteAndMes("%" + nome + "%", mes);
    }

}
