package med.voll.api.service.validacao.consulta.agendamento;

import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.domain.repository.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoPacienteAtivo implements IValidacaoConsultaAgendamento {
    @Autowired
    private PacienteRepository repository;

    public void validar(AgendamentoConsultaDTO dto) {
        if (!repository.findAtivoById(dto.idMedico())) {
            throw new ValidacaoException("Consulta nao pode ser realizada com medico excluido");
        }
    }
}
