package br.com.insidesoftwares.dayoffmarker.domain.repository.city;

import br.com.insidesoftwares.dayoffmarker.RepositoryTestApplication;
import br.com.insidesoftwares.dayoffmarker.RepositoryTestUtils;
import br.com.insidesoftwares.dayoffmarker.domain.entity.city.City;
import br.com.insidesoftwares.dayoffmarker.domain.entity.country.Country;
import br.com.insidesoftwares.dayoffmarker.domain.repository.country.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = "classpath:postgresql/setsup_database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:postgresql/delete_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(classes = RepositoryTestApplication.class)
@Testcontainers
class CityRepositoryIntegrationTest extends RepositoryTestUtils {

    @Container
    static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest");

    @DynamicPropertySource
    private static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Test
    void shouldReturnCityWhenInformedAnExistingID() {
        Optional<City> optionalCity = cityRepository.findCityById(CITY_BARB_ID);

        assertTrue(optionalCity.isPresent());
        optionalCity.ifPresent(city -> {
            assertEquals(CITY_BARB_ID, city.getId());
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
        Optional<City> optionalCity = cityRepository.findCityFullHolidayById(CITY_BARB_ID);

        assertTrue(optionalCity.isPresent());
        optionalCity.ifPresent(city -> {
            assertEquals(CITY_BARB_ID, city.getId());
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

        Page<City> cityPage = cityRepository.findCityByStateID(STATE_MG_ID, pageable);

        assertEquals(2, cityPage.getTotalPages());
        assertEquals(3, cityPage.getTotalElements());
        assertEquals(2, cityPage.getContent().size());

        assertEquals(CITY_MTCS_ID, cityPage.getContent().get(0).getId());
        assertEquals(CITY_VESP_ID, cityPage.getContent().get(1).getId());
    }

    @Test
    void shouldReturnPaginatedListOfCitiesWhenInformedStateIDAndPaginationConfigurationSortDESCAndOrderByID() {
        Pageable pageable = createPageable(1, 2, Sort.Direction.DESC, eOrderCity.ID.name());

        Page<City> cityPage = cityRepository.findCityByStateID(STATE_MG_ID, pageable);

        assertEquals(2, cityPage.getTotalPages());
        assertEquals(3, cityPage.getTotalElements());
        assertEquals(2, cityPage.getContent().size());

        assertEquals(CITY_BARB_ID, cityPage.getContent().get(0).getId());
        assertEquals(CITY_VESP_ID, cityPage.getContent().get(1).getId());
    }

    @Test
    void shouldReturnPaginatedListOfCitiesWhenInformedStateIDAndPaginationConfigurationSortDESCAndOrderByCode() {
        Pageable pageable = createPageable(1, 2, Sort.Direction.DESC, eOrderCity.CODE.name());

        Page<City> cityPage = cityRepository.findCityByStateID(STATE_MG_ID, pageable);

        assertEquals(2, cityPage.getTotalPages());
        assertEquals(3, cityPage.getTotalElements());
        assertEquals(2, cityPage.getContent().size());

        assertEquals(CITY_VESP_ID, cityPage.getContent().get(0).getId());
        assertEquals(CITY_MTCS_ID, cityPage.getContent().get(1).getId());

        assertEquals("VESP01", cityPage.getContent().get(0).getCode());
        assertEquals("MTCS01", cityPage.getContent().get(1).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfCitiesWhenInformedStateIDAndPaginationConfigurationSortDESCAndOrderByCodeAndAllItems() {
        Pageable pageable = createPageable(1, 6, Sort.Direction.DESC, eOrderCity.CODE.name());

        Page<City> cityPage = cityRepository.findCityByStateID(STATE_MG_ID, pageable);

        assertEquals(1, cityPage.getTotalPages());
        assertEquals(3, cityPage.getTotalElements());
        assertEquals(3, cityPage.getContent().size());

        assertEquals(CITY_VESP_ID, cityPage.getContent().get(0).getId());
        assertEquals(CITY_MTCS_ID, cityPage.getContent().get(1).getId());
        assertEquals(CITY_BARB_ID, cityPage.getContent().get(2).getId());

        assertEquals("VESP01", cityPage.getContent().get(0).getCode());
        assertEquals("MTCS01", cityPage.getContent().get(1).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfCitiesWhenInformedCountryIDAndPaginationConfigurationSortDESCAndOrderByCode() {
        Country country = countryRepository.getReferenceById(COUNTRY_BRS_ID);
        Pageable pageable = createPageable(1, 2, Sort.Direction.DESC, eOrderCity.CODE.name());

        Page<City> cityPage = cityRepository.findCityByCountry(country, pageable);

        assertEquals(5, cityPage.getTotalPages());
        assertEquals(9, cityPage.getTotalElements());
        assertEquals(2, cityPage.getContent().size());

        assertEquals(CITY_VESP_ID, cityPage.getContent().get(0).getId());
        assertEquals(CITY_TELIS_ID, cityPage.getContent().get(1).getId());

        assertEquals("VESP01", cityPage.getContent().get(0).getCode());
        assertEquals("TELIS01", cityPage.getContent().get(1).getCode());
    }

    @Test
    void shouldReturnPaginatedListOfCitiesWhenInformedTheStateIDAndConfigurationPaginationSortASCAndOrderByIDAndAllItems() {
        Country country = countryRepository.getReferenceById(COUNTRY_BRS_ID);
        Pageable pageable = createPageable(1, 12, Sort.Direction.ASC, eOrderCity.ID.name());

        Page<City> cityPage = cityRepository.findCityByCountry(country, pageable);

        assertEquals(1, cityPage.getTotalPages());
        assertEquals(9, cityPage.getTotalElements());
        assertEquals(9, cityPage.getContent().size());

        assertEquals(CITY_MTCS_ID, cityPage.getContent().get(0).getId());
        assertEquals(CITY_BEXO_ID, cityPage.getContent().get(1).getId());
        assertEquals(CITY_VESP_ID, cityPage.getContent().get(2).getId());

        assertEquals("MTCS01", cityPage.getContent().get(0).getCode());
        assertEquals("BEXO01", cityPage.getContent().get(1).getCode());
        assertEquals("VESP01", cityPage.getContent().get(2).getCode());
    }

    @Test
    void shouldReturnTrueWhenInformACityBarbacenaAndStateMinasGerais() {
        boolean exists = cityRepository.existsByNameAndStateID(CITY_NAME_BARBACENA, STATE_MG_ID);

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenInformingTheCityBarbacenaAndStateSaoPaulo() {
        boolean exists = cityRepository.existsByNameAndStateID(CITY_NAME_BARBACENA, STATE_SP_ID);

        assertFalse(exists);
    }

    @Test
    void shouldReturnFalseWhenInformingBarbacenaCityandNonexistentState() {
        boolean exists = cityRepository.existsByNameAndStateID(CITY_NAME_BARBACENA, CITY_ID_INVALID);

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndStateMinasGerais() {
        boolean exists = cityRepository.existsByCodeAndStateID(CITY_CODE_BARB01, STATE_MG_ID);

        assertTrue(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndStateSaoPaulo() {
        boolean exists = cityRepository.existsByCodeAndStateID(CITY_CODE_BARB01, STATE_SP_ID);

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndStateNonexistent() {
        boolean exists = cityRepository.existsByCodeAndStateID(CITY_CODE_BARB01, CITY_ID_INVALID);

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndAcronynmBarbAndStateMinasGerais() {
        boolean exists = cityRepository.existsByCodeAndAcronymAndStateID(CITY_CODE_BARB01, CITY_ACRONYM_BARB, STATE_MG_ID);

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenInformingACodeBarbacenaAndAcronynmPelisAndStateMinasGerais() {
        boolean exists = cityRepository.existsByCodeAndAcronymAndStateID(CITY_CODE_BARB01, CITY_ACRONYM_PELIS, STATE_MG_ID);

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndAcronynmBarbAndStateSaoPaulo() {
        boolean exists = cityRepository.existsByCodeAndAcronymAndStateID(CITY_CODE_BARB01, CITY_ACRONYM_BARB, STATE_SP_ID);

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndAcronynmBarbAndStateNonexistent() {
        boolean exists = cityRepository.existsByCodeAndAcronymAndStateID(CITY_CODE_BARB01, CITY_ACRONYM_BARB, UUID.randomUUID());

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCityBarbacenaAndStateMinasGeraisAndIDOfOutraCity() {
        boolean exists = cityRepository.existsByNameAndStateIDAndNotId(CITY_NAME_BARBACENA, STATE_MG_ID, CITY_ID_INVALID);

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenInformingCityBarbacenaAndStateMinasGeraisAndIDOfBarbacena() {
        boolean exists = cityRepository.existsByNameAndStateIDAndNotId(CITY_NAME_BARBACENA, STATE_SP_ID, CITY_BARB_ID);

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndStateMinasGeraisAndOtherID() {
        boolean exists = cityRepository.existsByCodeAndStateIDAndNotId(CITY_CODE_BARB01, STATE_MG_ID, CITY_ID_INVALID);

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenInformCodeBarbacenaAndStateMinasGeraisAndBarbacenaId() {
        boolean exists = cityRepository.existsByCodeAndStateIDAndNotId(CITY_CODE_BARB01, STATE_SP_ID, CITY_BARB_ID);

        assertFalse(exists);
    }

    @Test
    void shouldReturnTrueWhenInformingCodeBarbacenaAndAcronynmBarbAndStateMinasGeraisAndIDOtherCity() {
        boolean exists = cityRepository.existsByCodeAndAcronymAndStateIDAndNotId(CITY_CODE_BARB01, CITY_ACRONYM_BARB, STATE_MG_ID, CITY_ID_INVALID);

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenInformingCodeBarbacenaAndAcronynmBarbStateMinasGeraisAndBarbacenaId() {
        boolean exists = cityRepository.existsByCodeAndAcronymAndStateIDAndNotId(CITY_CODE_BARB01, CITY_ACRONYM_PELIS, STATE_MG_ID, CITY_BARB_ID);

        assertFalse(exists);
    }
}
