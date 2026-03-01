package med.voll.api.service.validacao.consulta.cancelamento;

import med.voll.api.domain.dto.CancelamentoConsultaDTO;

public interface IValidacaoConsultaCancelamento {
    void validar(CancelamentoConsultaDTO dados);
}
