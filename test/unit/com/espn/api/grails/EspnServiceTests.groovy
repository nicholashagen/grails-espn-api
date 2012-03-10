package com.espn.api.grails



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(EspnService)
class EspnServiceTests {

	void testGetTopHeadlines() {
		EspnService espnService = createService()
		assertNotNull(espnService.getTopHeadlines());
    }
	
	void testGetTopHeadlinesFuture() {
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
		EspnService espnService = new EspnService()
		espnService.afterPropertiesSet()
		
		return espnService
	}
}
