package br.com.empresa.citiesapi.states;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class StateResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Testa estados do CSV")
    @ParameterizedTest
    @CsvSource({ "Rio de Janeiro", "Minas Gerais" })
    public void deveConterEstado(String estado) throws Exception {
        this.mockMvc.perform(get("/states"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(estado)));

    }

    @DisplayName("Testa se o codigo refere-se ao estado")
    @ParameterizedTest
    @CsvSource({ "19, Rio de Janeiro", "11, Minas Gerais" })
    public void deveRetornarEstado(Integer codigoEstado, String estado) throws Exception {
        this.mockMvc.perform(get("/states/" + codigoEstado))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(estado)));

    }

    @DisplayName("Teste de código inválido")
    @ParameterizedTest
    @CsvSource({ "0", "30" })
    public void deveRetornarEstadoNaoEncontrado(Integer codigoEstado) throws Exception {
        this.mockMvc.perform(get("/states/" + codigoEstado))
                .andDo(print())
                .andExpect(status().isNotFound());

    }
}