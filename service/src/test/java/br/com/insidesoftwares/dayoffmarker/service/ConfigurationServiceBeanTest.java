package br.com.insidesoftwares.dayoffmarker.service;

import br.com.insidesoftwares.dayoffmarker.repository.ConfigurationRepository;
import br.com.insidesoftwares.dayoffmarker.repository.CountryRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ConfigurationServiceBean.class })
class ConfigurationServiceBeanTest {

	@MockBean
	private ConfigurationRepository configurationRepository;

	@MockBean
	private CountryRepository countryRepository;

	@Autowired
	private ConfigurationServiceBean configurationServiceBean;



}
