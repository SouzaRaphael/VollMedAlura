package med.voll.api.service.consulta;

import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.domain.repository.MedicoRepository;
import med.voll.api.infra.exception.ValidacaoException;

public class ValidacaoMedicoAtivo {
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
