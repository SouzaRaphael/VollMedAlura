package med.voll.api.service.validacao.consulta.agendamento;

import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaAgendamento")
public class ValidacaoHorarioAntecedencia implements IValidacaoConsultaAgendamento {
    public void validar(AgendamentoConsultaDTO dto) {
        var dataConsulta = dto.data();

        if (Duration.between(LocalDateTime.now(), dataConsulta).toMinutes() < 30) {
            throw new ValidacaoException("Consulta deve ser agendada com antecedencia de 30 minutos");
        }
    }
}
