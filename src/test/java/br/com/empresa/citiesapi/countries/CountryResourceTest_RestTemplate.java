package br.com.empresa.citiesapi.countries;


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
public class CountryResourceTest_RestTemplate {

    @LocalServerPort
    private int port;

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response;


    @DisplayName("Testa paises do CSV")
    @ParameterizedTest
    @CsvSource({ "Brazil", "Afghanistan" })
    public void deveConterPais(String codigoPais) throws Exception {
        response = restTemplate.getForEntity("http://localhost:" + port + "/countries", String.class);
        assertThat(response).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(codigoPais);

    }

    @DisplayName("Testa se o codigo refere-se ao pais")
    @ParameterizedTest
    @CsvSource({ "1, Brazil", "2, Afghanistan" })
    public void deveRetornarPais(Integer codigoPais, String pais) throws Exception {
        response = restTemplate.getForEntity("http://localhost:" + port + "/countries/" + codigoPais, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(pais);

    }

    @DisplayName("Valores válidos")
    @ParameterizedTest
    @CsvSource({ "1", "263" })
    public void deveRetornarPais_ValoresValidos(Integer codigoPais) throws Exception {
        response = restTemplate.getForEntity("http://localhost:" + port + "/countries/" + codigoPais, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


    }



}