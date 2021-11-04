package br.com.empresa.citiesapi.cities;


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
public class CityResourceTest_RestTemplate {

    @LocalServerPort
    private int port;

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response;


    @DisplayName("Testa cidades do CSV")
    @ParameterizedTest
    @CsvSource({ "Alegre", "Alto Rio Novo" })
    public void deveConterCidade(String cidade) throws Exception {
        response = restTemplate.getForEntity("http://localhost:" + port + "/cities", String.class);
        assertThat(response).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(cidade);

    }

    @DisplayName("Testa se o codigo refere-se à cidade")
    @ParameterizedTest
    @CsvSource({ "3658, Rio de Janeiro", "7, Anchieta" })
    public void deveRetornarCidade(Integer codigoCidade, String cidade) throws Exception {
        response = restTemplate.getForEntity("http://localhost:" + port + "/cities/" + codigoCidade, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains(cidade);

    }

    @DisplayName("Valores válidos")
    @ParameterizedTest
    @CsvSource({ "1", "5609"})
    public void deveRetornarCidade_ValoresValidos(Integer codigoCidade) throws Exception {
        response = restTemplate.getForEntity("http://localhost:" + port + "/cities/" + codigoCidade, String.class);
        assertThat(response).isNotNull();
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


    }






}