package com.espn.api.grails

class ApiResponseException extends RuntimeException {

	String statusCode
	String statusText
	Object responseObject
	
	public ApiResponseException(String statusCode, String statusText, Object responseObject = null, Throwable cause = null) {
		super(cause)
		this.statusCode = statusCode
		this.statusText = statusText
		this.responseObject = responseObject
	}
}
