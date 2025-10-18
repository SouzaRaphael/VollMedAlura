package med.voll.api.service;

import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.domain.models.Consulta;
import med.voll.api.domain.models.Medico;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.domain.repository.MedicoRepository;
import med.voll.api.domain.repository.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public void agendar(AgendamentoConsultaDTO dto) {
        if (!pacienteRepository.existsById(dto.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado nao existe");
        }

        if (dto.idMedico() != null && !medicoRepository.existsById(dto.idMedico())) {
            throw new ValidacaoException("Id do medico informado nao existe");
        }

        var paciente = pacienteRepository.getReferenceById(dto.idPaciente());
        var medico = escolherMedico(dto);
        Consulta consulta = new Consulta(null, medico, paciente, dto.data());
        consultaRepository.save(consulta);
    }

    private Medico escolherMedico(AgendamentoConsultaDTO dto) {
        if (dto.idMedico() != null) {
            return medicoRepository.getReferenceById(dto.idMedico());
        }

        if (dto.especialidade() == null) {
            throw new ValidacaoException("Especialidade e obrigatoria quando medico nao for escolhido");
        }

        return medicoRepository.escolherMedicoAleatorioData(dto.especialidade(), dto.data());
    }
}
