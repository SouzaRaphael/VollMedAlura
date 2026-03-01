package med.voll.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import med.voll.api.domain.models.Consulta;

import java.time.LocalDateTime;

public record DetalhamentoConsultaDTO(
        Long id,
        Long idMedico,
        Long idPaciente,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data
) {
    public DetalhamentoConsultaDTO(Consulta consulta) {
        this(
                consulta.getId(),
                consulta.getMedico().getId(),
                consulta.getPaciente().getId(),
                consulta.getData()
        );
    }
}