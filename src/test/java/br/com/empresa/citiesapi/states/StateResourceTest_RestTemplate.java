package br.com.empresa.citiesapi.states;


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
public class StateResourceTest_RestTemplate {

    @LocalServerPort
    private int port;

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response;


    @DisplayName("Testa estados do CSV")
    @ParameterizedTest
    @CsvSource({ "Rio de Janeiro", "Minas Gerais" })
    public void deveConterEstado(String estado) throws Exception {
        response = restTemplate.getForEntity("http://localhost:" + port + "/states", String.class);
        assertThat(response).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(estado);

    }

    @DisplayName("Testa se o codigo refere-se ao estado")
    @ParameterizedTest
    @CsvSource({ "19, Rio de Janeiro", "11, Minas Gerais" })
    public void deveRetornarEstado(Integer codigoEstado, String estado) throws Exception {
        response = restTemplate.getForEntity("http://localhost:" + port + "/states/" + codigoEstado, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(estado);

    }

    @DisplayName("Valores válidos")
    @ParameterizedTest
    @CsvSource({ "1", "27" })
    public void deveRetornarEstado_ValoresValidos(Integer codigoEstado) throws Exception {
        response = restTemplate.getForEntity("http://localhost:" + port + "/states/" + codigoEstado, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


    }



}