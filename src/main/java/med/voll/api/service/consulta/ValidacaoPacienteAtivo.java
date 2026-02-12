package med.voll.api.service.consulta;

import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.domain.repository.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;

public class ValidacaoPacienteAtivo {
    private PacienteRepository repository;

    public void validar(AgendamentoConsultaDTO dto) {
        if (!repository.findAtivoById(dto.idMedico())) {
            throw new ValidacaoException("Consulta nao pode ser realizada com medico excluido");
        }
    }
}
