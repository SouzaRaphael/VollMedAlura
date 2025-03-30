package med.voll.api.domain.dto;

import med.voll.api.domain.enums.EspecialidadeMedico;
import med.voll.api.domain.models.Endereco;
import med.voll.api.domain.models.Medico;

public record MedicoReturnDTO(
        Long id,
        String nome,
        String email,
        String crm,
        String telefone,
        EspecialidadeMedico especialidade,
        Endereco endendereco
) {
    public MedicoReturnDTO(Medico medico) {
        this(
                medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getTelefone(),
                medico.getCrm(),
                medico.getEspecialidade(),
                medico.getEndereco()
        );
    }
}
