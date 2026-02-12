package med.voll.api.service.consulta;

import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.infra.exception.ValidacaoException;

public class ValidacaoDisponibilidadeMedico {
    private ConsultaRepository repository;

    public void validar(AgendamentoConsultaDTO dto) {
        if (repository.existsByMedicoIdAndData(dto.idMedico(), dto.data())) {
            throw new ValidacaoException("Medico ja possui outra consulta agendada nesse mesmo horario.");
        }
    }
}
