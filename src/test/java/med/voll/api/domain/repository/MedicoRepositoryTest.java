package med.voll.api.domain.repository;

import med.voll.api.domain.dto.EnderecoDTO;
import med.voll.api.domain.dto.MedicoPostDTO;
import med.voll.api.domain.dto.PacientePostDTO;
import med.voll.api.domain.enums.EspecialidadeMedico;
import med.voll.api.domain.models.Consulta;
import med.voll.api.domain.models.Medico;
import med.voll.api.domain.models.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Deveria devolver null quando unico medico cadastrado nao esta disponivel na data")
    void escolherMedicoAleatorioDataCenario1() {
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", EspecialidadeMedico.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        //when ou act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioData(EspecialidadeMedico.CARDIOLOGIA, proximaSegundaAs10);

        //then ou assert
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
    void escolherMedicoAleatorioLivreNaDataCenario2() {
        //given ou arrange
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", EspecialidadeMedico.CARDIOLOGIA);

        //when ou act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioData(EspecialidadeMedico.CARDIOLOGIA, proximaSegundaAs10);

        //then ou assert
        assertThat(medicoLivre).isEqualTo(medico);
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        entityManager.persist(new Consulta(null, medico, paciente, data, null));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, EspecialidadeMedico especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        entityManager.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        entityManager.persist(paciente);
        return paciente;
    }

    private MedicoPostDTO dadosMedico(String nome, String email, String crm, EspecialidadeMedico especialidade) {
        return new MedicoPostDTO (
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private PacientePostDTO dadosPaciente(String nome, String email, String cpf) {
        return new PacientePostDTO (
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private EnderecoDTO dadosEndereco() {
        return new EnderecoDTO (
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}