package med.voll.api.service.validacao.consulta.agendamento;

import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidacaoHorarioFuncionamento implements IValidacaoConsultaAgendamento {
    public void validar(AgendamentoConsultaDTO dto) {
        var diaConsulta = dto.data().getDayOfWeek();
        var horaConsulta = dto.data().getHour();

        if (diaConsulta.equals(DayOfWeek.SUNDAY) || horaConsulta < 7 || horaConsulta > 18) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clinica.");
        }

    }
}
