package med.voll.api.service.validacao.consulta.cancelamento;

import med.voll.api.domain.dto.CancelamentoConsultaDTO;
import med.voll.api.domain.repository.ConsultaRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidacaoHorarioAntecedencia implements IValidacaoConsultaCancelamento {
    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(CancelamentoConsultaDTO dto) {
        var consulta = repository.getReferenceById(dto.idConsulta());
        var agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

        if (diferencaEmHoras < 24) {
            throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }
    }
}
