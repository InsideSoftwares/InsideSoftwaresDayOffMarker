package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.dayoffmarker.domain.repository.ConfigurationRepository;
import br.com.insidesoftwares.dayoffmarker.domain.repository.CountryRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConfigurationServiceBeanTest {

	@Mock
	private ConfigurationRepository configurationRepository;

	@Mock
	private CountryRepository countryRepository;

	@InjectMocks
	private ConfigurationServiceBean configurationServiceBean;




}
