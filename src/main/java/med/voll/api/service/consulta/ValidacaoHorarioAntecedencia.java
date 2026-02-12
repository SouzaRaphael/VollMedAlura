package med.voll.api.service.consulta;

import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.infra.exception.ValidacaoException;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidacaoHorarioAntecedencia {
    public void validar(AgendamentoConsultaDTO dto) {
        var dataConsulta = dto.data();

        if (Duration.between(LocalDateTime.now(), dataConsulta).toMinutes() < 30) {
            throw new ValidacaoException("Consulta deve ser agendada com antecedencia de 30 minutos");
        }
    }
}
