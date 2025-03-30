package med.voll.api.domain.dto;

import med.voll.api.domain.models.Paciente;

public record PacienteGetDTO(
        Long id,
        String nome,
        String email,
        String cpf
) {
    public PacienteGetDTO(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getCpf()
        );
    }
}