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
    @CsvSource({ "Alegre", "Alto Rio Novo" })
    public void deveConterCidade(String cidade) throws Exception {
        this.mockMvc.perform(get("/cities"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(cidade)));

    }

    @DisplayName("Testa se o codigo refere-se à cidade")
    @ParameterizedTest
    @CsvSource({ "3658, Rio de Janeiro", "7, Anchieta" })
    public void deveRetornarCidade(Integer codigoCidade, String cidade) throws Exception {
        this.mockMvc.perform(get("/cities/" + codigoCidade))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(cidade)));

    }

    @DisplayName("Valores inválidos - Limite Inferior e Superior")
    @ParameterizedTest
    @CsvSource({ "0", "5610"})
    public void deveRetornarErro_ValoresInvalidos(Integer codigoCidade) throws Exception {
        this.mockMvc.perform(get("/cities/" + codigoCidade))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @DisplayName("Valores válidos")
    @ParameterizedTest
    @CsvSource({ "1", "5609"})
    public void deveRetornarCidade_ValoresValidos(Integer codigoCidade) throws Exception {
        this.mockMvc.perform(get("/cities/" + codigoCidade))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @ParameterizedTest
    @CsvSource({ "4929, 5254, 12426.810463465172"})
    public void deveCalcularDistancia_UsandoCube(Integer codigoCidade1, Integer codigoCidade2, String distancia) throws Exception {
        this.mockMvc.perform(get("/distances/by-cube?from=" + codigoCidade1+ "&to=" + codigoCidade2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(distancia)));

    }


    @ParameterizedTest
    @CsvSource({ "4929, 5254, 7.57102293200459"})
    public void deveCalcularDistancia_UsandoPoints(Integer codigoCidade1, Integer codigoCidade2, String distancia) throws Exception {
        this.mockMvc.perform(get("/distances/by-points?from=" + codigoCidade1+ "&to=" + codigoCidade2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(distancia)));
    }


}