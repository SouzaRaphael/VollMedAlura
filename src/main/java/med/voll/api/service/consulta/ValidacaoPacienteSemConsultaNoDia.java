package med.voll.api.service.consulta;

import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.infra.exception.ValidacaoException;

public class ValidacaoPacienteSemConsultaNoDia {
    private ConsultaRepository repository;

    public void validar(AgendamentoConsultaDTO dto) {
        var primeiroHorario = dto.data().withHour(7);
        var ultimoHorario = dto.data().withHour(18);

        if (repository.existsByPacienteIdAndDataBetween(dto.idPaciente(), primeiroHorario, ultimoHorario)) {
            throw new ValidacaoException("Paciente ja possui horario agendado nesse dia");
        }
    }
}
