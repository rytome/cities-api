package br.com.empresa.citiesapi.countries;


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
public class CountryResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Testa paises do CSV")
    @ParameterizedTest
    @CsvSource({ "Brazil", "Afghanistan" })
    public void deveConterPais(String codigoPais) throws Exception {
        this.mockMvc.perform(get("/countries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(codigoPais)));

    }

    @DisplayName("Testa se o codigo refere-se ao pais")
    @ParameterizedTest
    @CsvSource({ "1, Brazil", "2, Afghanistan" })
    public void deveRetornarPais(Integer codigoPais, String pais) throws Exception {
        this.mockMvc.perform(get("/countries/" + codigoPais))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(pais)));

    }

    @DisplayName("Teste de código inválido")
    @ParameterizedTest
    @CsvSource({ "290", "543" })
    public void deveRetornarPaisNaoEncontrado(Integer codigoPais) throws Exception {
        this.mockMvc.perform(get("/countries/" + codigoPais))
                .andDo(print())
                .andExpect(status().isNotFound());

    }


}