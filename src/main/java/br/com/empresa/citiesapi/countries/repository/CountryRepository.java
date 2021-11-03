package br.com.empresa.citiesapi.countries.repository;

import br.com.empresa.citiesapi.countries.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
