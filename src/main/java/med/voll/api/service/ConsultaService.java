package med.voll.api.service;

import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.domain.dto.CancelamentoConsultaDTO;
import med.voll.api.domain.dto.DetalhamentoConsultaDTO;
import med.voll.api.domain.models.Consulta;
import med.voll.api.domain.models.Medico;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.domain.repository.MedicoRepository;
import med.voll.api.domain.repository.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.service.validacao.consulta.agendamento.IValidacaoConsultaAgendamento;
import med.voll.api.service.validacao.consulta.cancelamento.IValidacaoConsultaCancelamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<IValidacaoConsultaAgendamento> validadoresAgendamento;

    @Autowired
    private List<IValidacaoConsultaCancelamento> validadoresCancelamento;

    public DetalhamentoConsultaDTO agendar(AgendamentoConsultaDTO dto) {
        if (!pacienteRepository.existsById(dto.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado nao existe");
        }

        if (dto.idMedico() != null && !medicoRepository.existsById(dto.idMedico())) {
            throw new ValidacaoException("Id do medico informado nao existe");
        }

        validadoresAgendamento.forEach(v -> v.validar(dto));

        var paciente = pacienteRepository.getReferenceById(dto.idPaciente());

        var medico = escolherMedico(dto);
        if (medico == null) {
            throw new ValidacaoException("Nao existe medico disponivel nessa data");
        }

        Consulta consulta = new Consulta(null, medico, paciente, dto.data(), null);
        consultaRepository.save(consulta);

        return new DetalhamentoConsultaDTO(consulta);
    }

    public void cancelar(CancelamentoConsultaDTO dto) {
        if (!consultaRepository.existsById(dto.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dto));

        var consulta = consultaRepository.getReferenceById(dto.idConsulta());
        consulta.cancelar(dto.motivo());
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
