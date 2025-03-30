package med.voll.api.domain.dto;

import jakarta.validation.constraints.NotNull;

public record PacientePutDTO (
        @NotNull
        Long id,
        String nome,
        String telefone,
        EnderecoDTO endereco
) {}