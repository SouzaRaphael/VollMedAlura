package med.voll.api.domain.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.dto.EnderecoDTO;

@Embeddable

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String logradouro;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;
    private String numero;
    private String complemento;

    public Endereco(EnderecoDTO enderecoDTO) {
        this.logradouro = enderecoDTO.logradouro();
        this.bairro = enderecoDTO.bairro();
        this.cep = enderecoDTO.cep();
        this.cidade = enderecoDTO.cidade();
        this.uf = enderecoDTO.uf();
        this.numero = enderecoDTO.numero();
        this.complemento = enderecoDTO.complemento();
    }

    public void atualizarInformacoes(EnderecoDTO dados) {
        if (dados.logradouro() != null)
            this.logradouro = dados.logradouro();

        if (dados.bairro() != null)
            this.bairro = dados.bairro();

        if (dados.cep() != null)
            this.cep = dados.cep();

        if (dados.cidade() != null)
            this.cidade = dados.cidade();

        if (dados.uf() != null)
            this.uf = dados.uf();

        if (dados.numero() != null)
            this.numero = dados.numero();

        if (dados.complemento() != null)
            this.complemento = dados.complemento();
    }
}