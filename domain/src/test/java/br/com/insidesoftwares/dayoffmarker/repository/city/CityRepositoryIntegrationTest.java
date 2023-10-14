package br.com.insidesoftwares.dayoffmarker.repository.city;

import br.com.insidesoftwares.dayoffmarker.commons.enumeration.sort.eOrderCity;
import br.com.insidesoftwares.dayoffmarker.domain.entity.Country;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.City;
import br.com.insidesoftwares.dayoffmarker.domain.repository.CountryRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.city.CityRepository;
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

abstract class CityRepositoryIntegrationTest {

    private static final Long COUNTRY_ID = 1L;
    private static final Long STATE_MINAS_GERAIS_ID = 1L;
    private static final Long STATE_SAO_PAULO_ID = 2L;
    private static final Long CITY_BARBACENA_ID = 1L;
    private static final String CITY_NAME_BARBACENA = "Barbacena";
    private static final String CITY_CODE_BARB01 = "BARB01";
    private static final String CITY_ACRONYM_BARB = "BARB";
    private static final String CITY_ACRONYM_PELIS = "PELIS";
    private static final Long CITY_ID_INVALID = 9999L;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Test
    void shouldReturnCityWhenInformedAnExistingID() {
        Optional<City> optionalCity = cityRepository.findCityById(CITY_BARBACENA_ID);

        assertTrue(optionalCity.isPresent());
        optionalCity.ifPresent(city -> {
            assertEquals(CITY_BARBACENA_ID, city.getId());
            assertEquals(CITY_CODE_BARB01, city.getCode());
            assertEquals(CITY_ACRONYM_BARB, city.getAcronym());
        });
    }

    @Test
    void shouldntReturnCityWhenInformedOneIDNonexistent() {
        Optional<City> optionalCity = cityRepository.findCityById(CITY_ID_INVALID);

        assertFalse(optionalCity.isPresent());
    }

    @Test
    void shouldReturnCityWhenInformedExistingIDWithMethodFindCityFullHolidayById() {
        Optional<City> optionalCity = cityRepository.findCityFullHolidayById(CITY_BARBACENA_ID);

        assertTrue(optionalCity.isPresent());
        optionalCity.ifPresent(city -> {
            assertEquals(CITY_BARBACENA_ID, city.getId());
            assertEquals(CITY_CODE_BARB01, city.getCode());
            assertEquals(CITY_ACRONYM_BARB, city.getAcronym());
        });
    }

    @Test
    void shouldntReturnCityWhenInformedOneIDNonexistentWithMethodFindCityFullHolidayById() {
        Optional<City> optionalCity = cityRepository.findCityFullHolidayById(CITY_ID_INVALID);

        assertFalse(optionalCity.isPresent());
    }


    @Test
    void shouldReturnPaginatedListOfCitiesWhenInformedStateIDAndPaginationConfigurationSortASCAndOrderByID() {
        Pageable pageable = createPageable(1, 2, Sort.Direction.ASC, eOrderCity.ID.name());

        Page<City> cityPage = cityRepository.findCityByStateID(STATE_MINAS_GERAIS_ID, pageable);

        assertEquals(2, cityPage.getTotalPages());
        assertEquals(3, cityPage.getTotalElements());
        assertEquals(2, cityPage.getContent().size());

        assertEquals(1L, cityPage.getContent().get(0).getId());
        assertEquals(2L, cityPage.getContent().get(1).getId());
    }

    @Test
    void shouldReturnPaginatedListOfCitiesWhenInformedStateIDAndPaginationConfigurationSortDESCAndOrderByID() {
        Pageable pageable = createPageable(1, 2, Sort.Direction.DESC, eOrderCity.ID.name());

        Page<City> cityPage = cityRepository.findCityByStateID(STATE_MINAS_GERAIS_ID, pageable);

        assertEquals(2, cityPage.getTotalPages());
        assertEquals(3, cityPage.getTotalElements());
        assertEquals(2, cityPage.getContent().size());

        assertEquals(3L, cityPage.getContent().get(0).getId());
        assertEquals(2L, cityPage.getContent().get(1).getId());
    }

    @Test
    void shouldReturnPaginatedListOfCitiesWhenInformedStateIDAndPaginationConfigurationSortDESCAndOrderByCode() {
        Pageable pageable = createPageable(1, 2, Sort.Direction.DESC, eOrderCity.CODE.name());

        Page<City> cityPage = cityRepository.findCityByStateID(STATE_MINAS_GERAIS_ID, pageable);

        assertEquals(2, cityPage.getTotalPages());
        assertEquals(3, cityPage.getTotalElements());
        assertEquals(2, cityPage.getContent().size());

        assertEquals(3L, cityPage.getContent().get(0).getId());
        assertEquals(2L, cityPage.getContent().get(1).getId());

        assertEquals("VESP01", cityPage.getContent().get(0).getCode());
        assertEquals("MTCS01", cityPage.getContent().get(1).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfCitiesWhenInformedStateIDAndPaginationConfigurationSortDESCAndOrderByCodeAndAllItems() {
        Pageable pageable = createPageable(1, 6, Sort.Direction.DESC, eOrderCity.CODE.name());

        Page<City> cityPage = cityRepository.findCityByStateID(STATE_MINAS_GERAIS_ID, pageable);

        assertEquals(1, cityPage.getTotalPages());
        assertEquals(3, cityPage.getTotalElements());
        assertEquals(3, cityPage.getContent().size());

        assertEquals(3L, cityPage.getContent().get(0).getId());
        assertEquals(2L, cityPage.getContent().get(1).getId());
        assertEquals(1L, cityPage.getContent().get(2).getId());

        assertEquals("VESP01", cityPage.getContent().get(0).getCode());
        assertEquals("MTCS01", cityPage.getContent().get(1).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfCitiesWhenInformedCountryIDAndPaginationConfigurationSortDESCAndOrderByCode() {
        Country country = countryRepository.getReferenceById(COUNTRY_ID);
        Pageable pageable = createPageable(1, 2, Sort.Direction.DESC, eOrderCity.CODE.name());

        Page<City> cityPage = cityRepository.findCityByCountry(country, pageable);

        assertEquals(5, cityPage.getTotalPages());
        assertEquals(9, cityPage.getTotalElements());
        assertEquals(2, cityPage.getContent().size());

        assertEquals(3L, cityPage.getContent().get(0).getId());
        assertEquals(9L, cityPage.getContent().get(1).getId());

        assertEquals("VESP01", cityPage.getContent().get(0).getCode());
        assertEquals("TELIS01", cityPage.getContent().get(1).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfCitiesWhenInformedTheStateIDAndConfigurationPaginationSortASCAndOrderByIDAndAllItems() {
        Country country = countryRepository.getReferenceById(COUNTRY_ID);
        Pageable pageable = createPageable(1, 12, Sort.Direction.ASC, eOrderCity.ID.name());

        Page<City> cityPage = cityRepository.findCityByCountry(country, pageable);

        assertEquals(1, cityPage.getTotalPages());
        assertEquals(9, cityPage.getTotalElements());
        assertEquals(9, cityPage.getContent().size());

        assertEquals(1L, cityPage.getContent().get(0).getId());
        assertEquals(2L, cityPage.getContent().get(1).getId());
        assertEquals(3L, cityPage.getContent().get(2).getId());

        assertEquals("BARB01", cityPage.getContent().get(0).getCode());
        assertEquals("MTCS01", cityPage.getContent().get(1).getCode());
        assertEquals("VESP01", cityPage.getContent().get(2).getCode());
    }

    @Test
    void shouldReturnTrueWhenInformACityBarbacenaAndStateMinasGerais() {
        boolean exists = cityRepository.existsByNameAndStateID(CITY_NAME_BARBACENA, STATE_MINAS_GERAIS_ID);

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenInformingTheCityBarbacenaAndStateSaoPaulo() {
        boolean exists = cityRepository.existsByNameAndStateID(CITY_NAME_BARBACENA, STATE_SAO_PAULO_ID);

        assertFalse(exists);
    }

    @Test
    void shouldReturnFalseWhenInformingBarbacenaCityandNonexistentState() {
        boolean exists = cityRepository.existsByNameAndStateID(CITY_NAME_BARBACENA, 12311L);

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndStateMinasGerais() {
        boolean exists = cityRepository.existsByCodeAndStateID(CITY_CODE_BARB01, STATE_MINAS_GERAIS_ID);

        assertTrue(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndStateSaoPaulo() {
        boolean exists = cityRepository.existsByCodeAndStateID(CITY_CODE_BARB01, STATE_SAO_PAULO_ID);

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndStateNonexistent() {
        boolean exists = cityRepository.existsByCodeAndStateID(CITY_CODE_BARB01, 12311L);

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndAcronynmBarbAndStateMinasGerais() {
        boolean exists = cityRepository.existsByCodeAndAcronymAndStateID(CITY_CODE_BARB01, CITY_ACRONYM_BARB, STATE_MINAS_GERAIS_ID);

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenInformingACodeBarbacenaAndAcronynmPelisAndStateMinasGerais() {
        boolean exists = cityRepository.existsByCodeAndAcronymAndStateID(CITY_CODE_BARB01, CITY_ACRONYM_PELIS, STATE_MINAS_GERAIS_ID);

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndAcronynmBarbAndStateSaoPaulo() {
        boolean exists = cityRepository.existsByCodeAndAcronymAndStateID(CITY_CODE_BARB01, CITY_ACRONYM_BARB, STATE_SAO_PAULO_ID);

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndAcronynmBarbAndStateNonexistent() {
        boolean exists = cityRepository.existsByCodeAndAcronymAndStateID(CITY_CODE_BARB01, CITY_ACRONYM_BARB, 12311L);

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCityBarbacenaAndStateMinasGeraisAndIDOfOutraCity() {
        boolean exists = cityRepository.existsByNameAndStateIDAndNotId(CITY_NAME_BARBACENA, STATE_MINAS_GERAIS_ID, CITY_ID_INVALID);

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenInformingCityBarbacenaAndStateMinasGeraisAndIDOfBarbacena() {
        boolean exists = cityRepository.existsByNameAndStateIDAndNotId(CITY_NAME_BARBACENA, STATE_SAO_PAULO_ID, CITY_BARBACENA_ID);

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndStateMinasGeraisAndOtherID() {
        boolean exists = cityRepository.existsByCodeAndStateIDAndNotId(CITY_CODE_BARB01, STATE_MINAS_GERAIS_ID, CITY_ID_INVALID);

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenInformCodeBarbacenaAndStateMinasGeraisAndBarbacenaId() {
        boolean exists = cityRepository.existsByCodeAndStateIDAndNotId(CITY_CODE_BARB01, STATE_SAO_PAULO_ID, CITY_BARBACENA_ID);

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndAcronynmBarbAndStateMinasGeraisAndIDOtherCity() {
        boolean exists = cityRepository.existsByCodeAndAcronymAndStateIDAndNotId(CITY_CODE_BARB01, CITY_ACRONYM_BARB, STATE_MINAS_GERAIS_ID, CITY_ID_INVALID);

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenInformingCodeBarbacenaAndAcronynmBarbStateMinasGeraisAndBarbacenaId() {
        boolean exists = cityRepository.existsByCodeAndAcronymAndStateIDAndNotId(CITY_CODE_BARB01, CITY_ACRONYM_PELIS, STATE_MINAS_GERAIS_ID, CITY_BARBACENA_ID);

        assertFalse(exists);
    }
}
