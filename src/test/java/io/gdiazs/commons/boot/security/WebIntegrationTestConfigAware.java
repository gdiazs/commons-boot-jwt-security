package io.gdiazs.commons.boot.security;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mobile.device.DeviceResolverRequestFilter;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = CommonsSecurity.class)
@AutoConfigureMockMvc
public abstract class WebIntegrationTestConfigAware {


	@Autowired
	private WebApplicationContext context;

	protected MockMvc mockMvc;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;
	
	@Autowired
	private DeviceResolverRequestFilter deviceResolverRequestFilter;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilters(this.springSecurityFilterChain, deviceResolverRequestFilter).build();
	}
}
