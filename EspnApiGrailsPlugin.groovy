class EspnApiGrailsPlugin {
    
    // the plugin version
    def version = "0.1"
	
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    
    // the other plugins this plugin depends on
    def dependsOn = [:]
    
    // TODO Fill in these fields
    def title = "ESPN API Plugin"
    def author = "Nicholas Hagen"
    def authorEmail = "nicholas.hagen@znetdevelopment.com"
    def description = '''\
Plugin used to access the ESPN API in order to build sport-based products using the
core APIs provided by ESPN.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/espn-api"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Online location of the plugin's browseable source code.
    def scm = [ url: "git://github.com/nicholashagen/grails-espn-api.git" ]

    def doWithWebDescriptor = { xml ->
        // nothing to do
    }

    def doWithSpring = {
        // nothing to do
    }

    def doWithDynamicMethods = { ctx ->
        // nothing to do
    }

    def doWithApplicationContext = { applicationContext ->
        // nothing to do
    }

    def onChange = { event ->
        // nothing to do
    }

    def onConfigChange = { event ->
        // nothing to do
    }

    def onShutdown = { event ->
        // nothing to do
    }
}
