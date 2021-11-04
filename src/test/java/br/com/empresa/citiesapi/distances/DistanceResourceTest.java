package br.com.empresa.citiesapi.distances;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class DistanceResourceTest {

    @Autowired
    private MockMvc mockMvc;


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