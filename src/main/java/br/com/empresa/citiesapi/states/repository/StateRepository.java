package br.com.empresa.citiesapi.states.repository;

import br.com.empresa.citiesapi.states.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
