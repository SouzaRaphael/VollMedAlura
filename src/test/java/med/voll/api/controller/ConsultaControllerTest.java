package med.voll.api.controller;

import med.voll.api.domain.dto.AgendamentoConsultaDTO;
import med.voll.api.domain.dto.DetalhamentoConsultaDTO;
import med.voll.api.domain.enums.EspecialidadeMedico;
import med.voll.api.service.ConsultaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AgendamentoConsultaDTO> agendamentoConsultaJson;

    @Autowired
    private JacksonTester<DetalhamentoConsultaDTO> detalhamentoConsultaJson;

    @MockitoBean
    private ConsultaService service;

    @Test
    @DisplayName("Deveria devolver codigo 400 quando informacoes estao invalidas")
    @WithMockUser
    void agendarCenario1() throws Exception {
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo 200 quando informacoes estao validas")
    @WithMockUser
    void agendarCenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = EspecialidadeMedico.CARDIOLOGIA;

        var detalhamentoConsulta = new DetalhamentoConsultaDTO(null, 2l, 5l, data);

        when(service.agendar(any())).thenReturn(detalhamentoConsulta);

        var response = mvc
                .perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(agendamentoConsultaJson.write(
                                        new AgendamentoConsultaDTO(
                                                2l,
                                                5l,
                                                data,
                                                especialidade

                                        )
                                ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = detalhamentoConsultaJson.write(detalhamentoConsulta).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}