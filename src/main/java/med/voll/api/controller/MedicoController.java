package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.MedicoGetDTO;
import med.voll.api.dto.MedicoPostDTO;
import med.voll.api.dto.MedicoPutDTO;
import med.voll.api.models.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid MedicoPostDTO medico) {
        repository.save(new Medico(medico));
    }

    @GetMapping
    public Page<MedicoGetDTO> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(MedicoGetDTO::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid MedicoPutDTO dados) {
        Medico medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
//        repository.deleteById(id);
        Medico medico = repository.getReferenceById(id);
        medico.excluir();
    }
}