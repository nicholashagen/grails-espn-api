package com.espn.api.grails

import net.sf.json.JSONObject
import net.sf.json.JsonConfig
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import org.joda.time.DateTime;
import net.sf.ezmorph.*;
import net.sf.json.util.*;
import java.util.concurrent.*;
import javax.annotation.*;
import org.springframework.beans.factory.*;

// TODO: javadoc

class EspnService implements InitializingBean {

	static transactional = false
	
	static String apiUrl = "http://api.espn.com"
	static String apiVersion = "v1"
	
	def grailsApplication
	
	String apiKey
	ExecutorService executor 

	static Map sports =	[
		'baseball' : [ 
			'baseball/mlb'
		],
	
		'basketball' : [
			'basketball/mens-college-basketball',
			'basketball/nba',
			'basketball/wnba',
			'basketball/womens-college-basketball'
		],
	
		'boxing' : [ 'boxing' ],
		
		'football' : [
			'football/college-football',
			'football/nfl'
		],
	
		'golf' : [ 'golf' ],
		
		'hockey' : [ 'hockey/nhl' ],
		
		'horse-racing' : [ 'horse-racing' ],
		
		'mma' : [ 'mma' ],
		
		'racing' : [ 'racing', 'racing/nascar' ],
		
		'soccer' : [ 'soccer' ],
		
		'tennis' : [ 'tennis' ]
	]
	
	public EspnService() {
		super()
	}
	
	public void afterPropertiesSet() {
		
		// setup api settings
		apiKey = grailsApplication?.config?.com?.espn?.api?.key
		if (!apiKey) {
			apiKey = System.getProperty('com.espn.api.key');
			if (!apiKey) {
				throw new IllegalStateException("missing API key configuration: com.espn.api.key")
			}
		}
		
		// register custom joda-based date/time property for JSON
		MorpherRegistry registry = JSONUtils.getMorpherRegistry()
		registry.clear(java.util.Date)
		registry.registerMorpher(new ObjectMorpher() {

			public Class<?> morphsTo() { 
				return Date.class; 
			}
			
			public boolean supports(Class<?> type) { 
				return String.class.equals(type) || Date.class.equals(type); 
			}
			
			public Object morph(Object value) {
				if (value == null || Date.class.equals(value.class)) {
					return value;
				}

				def result = DateTime.parse(value).toDate();
				return result;
			}
		});

		// startup executor service for callback-based processor
		def threads = Runtime.getRuntime().availableProcessors()
		def property = grailsApplication?.config?.com?.espn?.api?.executor?.threads
		if (property) { 
			threads = property.toString().toInteger()
		}

		this.executor = Executors.newFixedThreadPool(threads);
	}

	List<String> getSports() {
		return sports.values().flatten()
	}
	
	List<String> getSports(String type) {
		assert(type != null)
		return sports[type] ?: []
	}
	
	HeadlinesResponse getTopHeadlines(Map params = null) {
		return _getTopHeadlines(params, null)
	}
	
	Future<HeadlinesResponse> getTopHeadlines(Map params = null, Closure callback) {
		assert(callback != null)
		return _getTopHeadlines(params, callback)
	}
	
	HeadlinesResponse getHeadlines(Map params = null) {
		return _getHeadlines(null, params, null)
	}
	
	Future<HeadlinesResponse> getHeadlines(Map params = null, Closure callback) {
		assert(callback != null)
		return _getHeadlines(null, params, callback)
	}
	
	HeadlinesResponse getHeadlines(String sport, Map params = null) {
		assert(sport != null)
		return _getHeadlines(sport, params, null)
	}
	
	Future<HeadlinesResponse> getHeadlines(String sport, Map params = null, Closure callback) {
		assert(sport != null)
		assert(callback != null)
		return _getHeadlines(sport, params, callback)
	}
	
	HeadlinesResponse getNews(Date date = null, Map params = null) {
		return _getNews(null, date, params, null)
	}
	
	Future<HeadlinesResponse> getNews(Date date = null, Map params = null, Closure callback) {
		assert(callback != null)
		return _getNews(null, date, params, callback)
	}
	
	HeadlinesResponse getNews(String sport, Date date = null, Map params = null) {
		assert(sport != null)
		return _getNews(sport, date, params, null)
	}
	
	Future<HeadlinesResponse> getNews(String sport, Date date = null, Map params = null, Closure callback) {
		assert(sport != null)
		assert(callback != null)
		return _getNews(sport, date, params, callback)
	}

	HeadlinesResponse getStory(Integer id, Map params = null) {
		assert(id != null)
		return _getStory(id, params, null)
	}
	
	Future<HeadlinesResponse> getStory(Integer id, Map params = null, Closure callback) {
		assert(id != null)
		assert(callback != null)
		return _getStory(id, params, callback)
	}
	
	HeadlinesResponse getTeamNews(Integer teamId, Date date = null, Map params = null) {
		assert(teamId != null)
		return _getTeamNews(teamId, date, params, null)
	}
	
	Future<HeadlinesResponse> getTeamNews(Integer teamId, Date date = null, Map params = null, Closure callback) {
		assert(teamId != null)
		assert(callback != null)
		return _getTeamNews(teamId, date, params, callback)
	}
	
	HeadlinesResponse getAthleteNews(Integer athleteId, Date date = null, Map params = null) {
		assert(athleteId != null)
		return _getAthleteNews(athleteId, date, params, null)
	}
	
	Future<HeadlinesResponse> getAthleteNews(Integer athleteId, Date date = null, Map params = null, Closure callback) {
		assert(athleteId != null)
		assert(callback != null)
		return _getAthleteNews(athleteId, date, params, callback)
	}

	private def _getTopHeadlines(Map params, Closure callback) {
		StringBuilder resource = prefix(null)
		resource.append('/news/headlines/top')
		
		return invoke(HeadlinesResponse.class, resource, params, callback);
	}
	
	private def _getHeadlines(String sport, Map params, Closure callback) {
		StringBuilder resource = prefix(sport)
		resource.append('/news/headlines')
		
		return invoke(HeadlinesResponse.class, resource, params, callback);
	}
	
	private def _getNews(String sport, Date date, Map params, Closure callback) {
		StringBuilder resource = prefix(sport)
		resource.append('/news')
		if (date) { 
			resource.append('/dates/').append(date.format('yyyyMMdd')) 
		}

		return invoke(HeadlinesResponse.class, resource, params, callback);
	}
	
	private def _getTeamNews(Integer teamId, Date date, Map params, Closure callback) {
		StringBuilder resource = prefix(null)
		resource.append('/teams/').append(teamId)
		resource.append('/news')
		if (date) { 
			resource.append('/dates/').append(date.format('yyyyMMdd')) 
		}

		return invoke(HeadlinesResponse.class, resource, params, callback);
	}
	
	private def _getAthleteNews(Integer athleteId, Date date, Map params, Closure callback) {
		StringBuilder resource = prefix(null)
		resource.append('/athletes/').append(athleteId)
		resource.append('/news')
		if (date) { 
			resource.append('/dates/').append(date.format('yyyyMMdd')) 
		}

		return invoke(HeadlinesResponse.class, resource, params, callback);
	}
	
	private def _getStory(Integer id, Map params = null, Closure callback = null) {
		assert(id != null)
		StringBuilder resource = prefix(null)
		resource.append('/news/').append(id)

		return invoke(HeadlinesResponse.class, resource, params, callback);
	}

	private StringBuilder prefix(String sport) {
		StringBuilder resource = new StringBuilder(128)
		resource.append('/sports');
		if (sport) { 
			resource.append('/').append(sport) 
		}
		
		return resource
	}
	
	private def invoke(Class<?> responseType, StringBuilder path, Map params, Closure callback) {
		return invoke(responseType, path.toString(), params, callback);
	}
	
	private def invoke(Class<?> responseType, String path, Map params, Closure callback) {
		if (callback) {
			return this.executor.submit((Callable) {
				def result = invokeApi(responseType, path) 
				callback(result)
				return result
			})
		}
		else {
			return invokeApi(responseType, path)
		}
	}
	
	// TODO: params.model [ pojo, json, xml ]
	// TODO: paging params
	private def invokeApi(Class<?> type, String path, def params = null) {
		def result = null;
		def http = new HTTPBuilder(apiUrl)
		http.request(GET, JSON) {
			uri.path = "/${apiVersion}/${path}"
			uri.query = [ 'apikey' : apiKey, '_accept' : 'application/json' ]
			headers.Accept = 'application/json'

			response.success = { resp, json ->
				if (json.status != 'success') {
					throw new ApiResponseException(resp.statusLine.statusCode, resp.statusLine.reasonPhrase, json)
				}
				
				result = convertJsonToPojo(json, type)
			}
			
			response.failure = { resp, reader ->
				throw new ApiResponseException(resp.statusLine.statusCode, resp.statusLine.reasonPhrase)
			}
		}
		
		return result;
	}
	
	private Object convertJsonToPojo(def json, def clazz) {
		JsonConfig config = new JsonConfig();
		config.setRootClass(clazz);
		return JSONObject.toBean(json, config)
	}
}
