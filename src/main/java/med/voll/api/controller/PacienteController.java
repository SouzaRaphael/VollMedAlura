package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.PacienteGetDTO;
import med.voll.api.dto.PacientePostDTO;
import med.voll.api.dto.PacientePutDTO;
import med.voll.api.models.Paciente;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid PacientePostDTO dados) {
        repository.save(new Paciente(dados));
    }

    @GetMapping
    public Page<PacienteGetDTO> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(PacienteGetDTO::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid PacientePutDTO dados) {
        Paciente paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        Paciente paciente = repository.getReferenceById(id);
        paciente.excluir();
    }
}