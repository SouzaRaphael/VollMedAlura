package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.domain.dto.DetalhamentoConsultaDTO;
import med.voll.api.service.consulta.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService service;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid AgendamentoConsultaDTO dto) {
        service.agendar(dto);
        return ResponseEntity.ok(new DetalhamentoConsultaDTO(null, null, null, null));
    }
}
