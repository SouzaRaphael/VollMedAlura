package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.domain.dto.CancelamentoConsultaDTO;
import med.voll.api.domain.dto.DetalhamentoConsultaDTO;
import med.voll.api.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ConsultaService service;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid AgendamentoConsultaDTO agendamentoDto) {
        var consultaDto = service.agendar(agendamentoDto);
        return ResponseEntity.ok(consultaDto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid CancelamentoConsultaDTO cancelamentoDto) {
        service.cancelar(cancelamentoDto);
        return ResponseEntity.ok().build();
    }
}
