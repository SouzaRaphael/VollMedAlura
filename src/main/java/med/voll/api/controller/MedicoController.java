package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.dto.MedicoGetDTO;
import med.voll.api.domain.dto.MedicoPostDTO;
import med.voll.api.domain.dto.MedicoPutDTO;
import med.voll.api.domain.dto.MedicoReturnDTO;
import med.voll.api.domain.models.Medico;
import med.voll.api.domain.repository.MedicoRepository;
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
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid MedicoPostDTO dados, UriComponentsBuilder uriBuilder) {
        Medico medico = new Medico(dados);
        repository.save(medico);

        URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new MedicoReturnDTO(medico));
    }

    @GetMapping
    public ResponseEntity<Page<MedicoGetDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        Page<MedicoGetDTO> page =  repository.findAllByAtivoTrue(paginacao).map(MedicoGetDTO::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid MedicoPutDTO dados) {
        Medico medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new MedicoReturnDTO(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
//        repository.deleteById(id);
        Medico medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        Medico medico = repository.getReferenceById(id);

        return ResponseEntity.ok(new MedicoReturnDTO(medico));
    }
}