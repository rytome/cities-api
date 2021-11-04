package br.com.empresa.citiesapi.cities;

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
public class CityResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Testa cidades do CSV")
    @ParameterizedTest
    @CsvSource({ "Afonso Cláudio", "Água Doce do Norte" })
    public void deveConterCidade(String cidade) throws Exception {
        this.mockMvc.perform(get("/cities"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(cidade)));

    }

    @DisplayName("Testa se o codigo refere-se ao estado")
    @ParameterizedTest
    @CsvSource({ "19, Rio de Janeiro", "11, Minas Gerais" })
    public void deveRetornarCidade(Integer codigoCidade, String cidade) throws Exception {
        this.mockMvc.perform(get("/cities/" + codigoCidade))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(cidade)));

    }

    @DisplayName("Teste de código inválido")
    @ParameterizedTest
    @CsvSource({ "0", "30" })
    public void deveRetornarCidadeNaoEncontrada(Integer codigoCidade) throws Exception {
        this.mockMvc.perform(get("/cities/" + codigoCidade))
                .andDo(print())
                .andExpect(status().isNotFound());

    }
}