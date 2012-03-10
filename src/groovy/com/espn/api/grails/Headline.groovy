package com.espn.api.grails

class Headline {

	int id
	String headline
	String[] keywords
	Date lastModified
	boolean premium
	Map links
	String type
	Related[] related
	String story
	String mobileStory
	String title
	String linkText
	String source
	String description
	Image[] images
	Category[] categories
	Date published
	Video[] video
	Audio[] audio
	Integer gameId
	String byline
}
