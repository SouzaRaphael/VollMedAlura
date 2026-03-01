package med.voll.api.service.validacao.consulta.agendamento;

import med.voll.api.domain.dto.AgendamentoConsultaDTO;

public interface IValidacaoConsultaAgendamento {
    void validar(AgendamentoConsultaDTO dto);
}
