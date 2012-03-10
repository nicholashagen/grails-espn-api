package com.espn.api.grails



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(EspnService)
class EspnServiceTests {

	final String API_KEY = ''
	
	void testGetTopHeadlines() {
		if (!checkApiKey()) { return; }
		
		EspnService espnService = createService()
		assertNotNull(espnService.getTopHeadlines());
    }
	
	void testGetTopHeadlinesFuture() {
		if (!checkApiKey()) { return; }
		
		def result = null
		EspnService espnService = createService()
		def future = espnService.getTopHeadlines { headlines ->
			result = headlines
		};
	
		assertNotNull(future.get())
		assertNotNull(result)
		assertEquals(future.get(), result)
	}
	
	private EspnService createService() {
		System.setProperty('com.espn.api.key', API_KEY)
		EspnService espnService = new EspnService()
		espnService.afterPropertiesSet()
		
		return espnService
	}
	
	private boolean checkApiKey() {
		if (!API_KEY) {
			println 'WARNING: no API key defined....skipping tests'
			return false
		}
		
		return true
	}
}
