package med.voll.api.domain.dto;

import med.voll.api.domain.models.Medico;

public record MedicoGetDTO(
        Long id,
        String nome,
        String email,
        String crm,
        String especialidade
) {
    public MedicoGetDTO(Medico medico) {
        this(
                medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getEspecialidade().toString()
        );
    }
}