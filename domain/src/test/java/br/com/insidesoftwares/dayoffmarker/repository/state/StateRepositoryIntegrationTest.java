package br.com.insidesoftwares.dayoffmarker.repository.state;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderCity;
import br.com.insidesoftwares.dayoffmarker.domain.entity.Country;
import br.com.insidesoftwares.dayoffmarker.domain.entity.state.State;
import br.com.insidesoftwares.dayoffmarker.domain.repository.CountryRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.state.StateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static br.com.insidesoftwares.dayoffmarker.RepositoryTestUtils.createPageable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class StateRepositoryIntegrationTest {

	@Autowired
	private StateRepository stateRepository;
    @Autowired
    private CountryRepository countryRepository;

    private static final Long COUNTRY_ID = 1L;

    private static final Long STATE_RIO_JANEIRO_ID = 3L;
    private static final Long STATE_ID_INVALID = 9999L;

	@Test
	void shouldReturnEstadoRioDeJaneiroWhenInformedIDCorrect(){

		Optional<State> state = stateRepository.findStateById(STATE_RIO_JANEIRO_ID);

		assertTrue(state.isPresent());
		assertEquals(3L, state.get().getId());
		assertEquals("RJ01", state.get().getCode());
	}

	@Test
	void shouldntReturnStatusWhenInformedIDInvalid(){

		Optional<State> state = stateRepository.findStateById(STATE_ID_INVALID);

		assertFalse(state.isPresent());
	}

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedTheCountryAndConfigurationPaginacaoSortASCAndOrderByID() {
        Country country = countryRepository.getReferenceById(COUNTRY_ID);
        Pageable pageable = createPageable(1, 1, Sort.Direction.ASC, eOrderCity.ID);

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(3, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(1, statePage.getContent().size());

        assertEquals(1L, statePage.getContent().get(0).getId());

        assertEquals("MG01", statePage.getContent().get(0).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedOEstadoIDAndSettingsPaginacaoSortDESCAndOrderByID() {
        Country country = countryRepository.getReferenceById(COUNTRY_ID);
        Pageable pageable = createPageable(1, 1, Sort.Direction.DESC, eOrderCity.ID);

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(3, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(1, statePage.getContent().size());

        assertEquals(3L, statePage.getContent().get(0).getId());

        assertEquals("RJ01", statePage.getContent().get(0).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedTheCountryAndConfigurationPaginacaoSortASCAndOrderByCode() {
        Country country = countryRepository.getReferenceById(COUNTRY_ID);
        Pageable pageable = createPageable(1, 1, Sort.Direction.ASC, eOrderCity.CODE);

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(3, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(1, statePage.getContent().size());

        assertEquals(1L, statePage.getContent().get(0).getId());

        assertEquals("MG01", statePage.getContent().get(0).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedOEstadoIDAndSettingsPaginacaoSortDESCAndOrderByCode() {
        Country country = countryRepository.getReferenceById(COUNTRY_ID);
        Pageable pageable = createPageable(1, 1, Sort.Direction.DESC, eOrderCity.CODE);

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(3, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(1, statePage.getContent().size());

        assertEquals(2L, statePage.getContent().get(0).getId());

        assertEquals("SP01", statePage.getContent().get(0).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedTheCountryAndConfigurationPaginacaoSortASCAndOrderByCodeAndAllItems() {
        Country country = countryRepository.getReferenceById(COUNTRY_ID);
        Pageable pageable = createPageable(1, 5, Sort.Direction.ASC, eOrderCity.CODE);

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(1, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(3, statePage.getContent().size());

        assertEquals(1L, statePage.getContent().get(0).getId());
        assertEquals(3L, statePage.getContent().get(1).getId());
        assertEquals(2L, statePage.getContent().get(2).getId());

        assertEquals("MG01", statePage.getContent().get(0).getCode());
        assertEquals("RJ01", statePage.getContent().get(1).getCode());
        assertEquals("SP01", statePage.getContent().get(2).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfStatesWhenInformedOEstadoIDAndSettingsPaginacaoSortDESCAndOrderByCodeAndAllItems() {
        Country country = countryRepository.getReferenceById(COUNTRY_ID);
        Pageable pageable = createPageable(1, 5, Sort.Direction.DESC, eOrderCity.CODE);

        Page<State> statePage = stateRepository.findAllByCountry(country, pageable);

        assertEquals(1, statePage.getTotalPages());
        assertEquals(3, statePage.getTotalElements());
        assertEquals(3, statePage.getContent().size());

        assertEquals(2L, statePage.getContent().get(0).getId());
        assertEquals(3L, statePage.getContent().get(1).getId());
        assertEquals(1L, statePage.getContent().get(2).getId());

        assertEquals("SP01", statePage.getContent().get(0).getCode());
        assertEquals("RJ01", statePage.getContent().get(1).getCode());
        assertEquals("MG01", statePage.getContent().get(2).getCode());
    }

}
