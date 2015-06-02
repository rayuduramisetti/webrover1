
// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images*//*', '/css*//*', '/js*//*', '/plugins*//*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false
ev3 {
            robot {
                name = 'groovybot'
                address = '192.168.100.108'
                sensor = 'ultrasonic'
                // values are N(NXT), L(EV3 Large), M (EV3 Medium), or G (MindsensorsGlideWheel)
                motorA = 'L'
                motorB = 'M'
                motorC = 'L'
                //  URL for iOS using app WebOfCam
                //ipwebcam = "http://192.168.1.192:8080/image.jpeg"
                // Below URL is used for some android camera app
                ipwebcam = 'http://192.168.100.112:8080/shot.jpg'

            }
        }

