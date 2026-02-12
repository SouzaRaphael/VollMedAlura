package med.voll.api.service.consulta;

import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.infra.exception.ValidacaoException;

import java.time.DayOfWeek;

public class ValidacaoHorarioFuncionamento {
    public void validar(AgendamentoConsultaDTO dto) {
        var diaConsulta = dto.data().getDayOfWeek();
        var horaConsulta = dto.data().getHour();

        if (diaConsulta.equals(DayOfWeek.SUNDAY) || horaConsulta < 7 || horaConsulta > 18) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clinica.");
        }

    }
}
