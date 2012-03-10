package com.espn.api.grails

class Response {

	Date timestamp
	String status
	
	int resultsOffset
	int resultsLimit
	int resultsCount
	
	boolean isValid() {
		return "success".equals(status)
	}
}
