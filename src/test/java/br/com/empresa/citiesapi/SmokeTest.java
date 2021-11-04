package br.com.empresa.citiesapi;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.empresa.citiesapi.countries.CountryResource;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private CountryResource countryResource;

    @Test
    public void contextLoads() throws Exception {
        assertThat(countryResource).isNotNull();
    }
}