package med.voll.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.enums.EspecialidadeMedico;

import java.time.LocalDateTime;

public record CancelamentoConsultaDTO(
        Long idConsulta,
        Long idMedico,
        @NotNull
        Long idPaciente,
        @NotNull
        @Future
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data,
        EspecialidadeMedico especialidade
) {}