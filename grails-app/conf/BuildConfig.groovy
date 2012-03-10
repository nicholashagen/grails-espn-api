grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") { }
	
    log "warn"
	
    repositories {
        grailsCentral()
    }

	dependencies {
		compile 'org.codehaus.groovy.modules.http-builder:http-builder:0.5.2'
    }

    plugins {
        build(":tomcat:$grailsVersion", ":release:1.0.0") {
            export = false
        }

		compile ":joda-time:1.3.1"
    }
}
