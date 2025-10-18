package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.dto.PacienteGetDTO;
import med.voll.api.domain.dto.PacientePostDTO;
import med.voll.api.domain.dto.PacientePutDTO;
import med.voll.api.domain.dto.PacienteReturnDTO;
import med.voll.api.domain.models.Paciente;
import med.voll.api.domain.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid PacientePostDTO dados, UriComponentsBuilder uriBuilder) {
        Paciente paciente = new Paciente(dados);
        repository.save(paciente);

        URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new PacienteReturnDTO(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<PacienteGetDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        Page<PacienteGetDTO> page = repository.findAllByAtivoTrue(paginacao).map(PacienteGetDTO::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid PacientePutDTO dados) {
        Paciente paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new PacienteReturnDTO(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        Paciente paciente = repository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        Paciente paciente = repository.getReferenceById(id);

        return ResponseEntity.ok(new PacienteReturnDTO(paciente));
    }
}