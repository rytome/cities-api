package br.com.empresa.citiesapi.distances;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistanceResourceTest_RestTemplate {

    @LocalServerPort
    private int port;

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response;

    @ParameterizedTest
    @CsvSource({ "4929, 5254, 12426.810463465172","758, 107, 774544.9579669912"})
    public void deveCalcularDistancia_UsandoCube(Integer codigoCidade1, Integer codigoCidade2, String distancia) throws Exception {
        response = restTemplate.getForEntity("http://localhost:" + port + "/distances/by-cube?from=" + codigoCidade1+ "&to=" + codigoCidade2, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(distancia);

    }

    @ParameterizedTest
    @CsvSource({ "4929, 5254, 7.57102293200459","758, 107, 407.13113441035097"})
    public void deveCalcularDistancia_UsandoPoints(Integer codigoCidade1, Integer codigoCidade2, String distancia) throws Exception {
        response = restTemplate.getForEntity("http://localhost:" + port + "/distances/by-points?from=" + codigoCidade1+ "&to=" + codigoCidade2, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(distancia);
    }


}