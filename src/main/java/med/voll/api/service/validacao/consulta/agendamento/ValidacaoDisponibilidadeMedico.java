package med.voll.api.service.validacao.consulta.agendamento;

import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoDisponibilidadeMedico implements IValidacaoConsultaAgendamento {
    @Autowired
    private ConsultaRepository repository;

    public void validar(AgendamentoConsultaDTO dto) {
        if (repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dto.idMedico(), dto.data())) {
            throw new ValidacaoException("Medico ja possui outra consulta agendada nesse mesmo horario.");
        }
    }
}
