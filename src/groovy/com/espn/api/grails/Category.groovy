package com.espn.api.grails

class Category {
	String description
	String type
	Integer sportId
	Integer teamId
	Integer athleteId
	Integer leagueId
	CategoryDefinition definition
	
	CategoryDefinition league
	CategoryDefinition team
	CategoryDefinition athlete
	
}
