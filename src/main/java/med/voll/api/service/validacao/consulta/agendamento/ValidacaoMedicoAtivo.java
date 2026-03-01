package med.voll.api.service.validacao.consulta.agendamento;

import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.domain.repository.MedicoRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoMedicoAtivo implements IValidacaoConsultaAgendamento {
    @Autowired
    private MedicoRepository repository;

    public void validar(AgendamentoConsultaDTO dto) {
        if (dto.idMedico() == null) {
            return;
        }

        if (!repository.findAtivoById(dto.idMedico())) {
            throw new ValidacaoException("Consulta nao pode ser realizada com medico excluido");
        }
    }
}
