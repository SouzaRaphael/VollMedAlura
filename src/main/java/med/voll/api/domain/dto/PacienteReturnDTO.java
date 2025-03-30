package med.voll.api.domain.dto;

import med.voll.api.domain.models.Endereco;
import med.voll.api.domain.models.Paciente;

public record PacienteReturnDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco
) {
    public PacienteReturnDTO(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getCpf(),
                paciente.getEndereco()
        );
    }
}
