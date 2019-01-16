package ru.vyarus.guicey.gsp

import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import ru.vyarus.dropwizard.guice.test.spock.ConfigOverride
import ru.vyarus.dropwizard.guice.test.spock.UseDropwizardApp
import ru.vyarus.guicey.gsp.support.app.SampleTemplateResource
import spock.lang.Specification

/**
 * @author Vyacheslav Rusakov
 * @since 15.01.2019
 */
@UseDropwizardApp(value = App, configOverride = [
        @ConfigOverride(key = "server.rootPath", value = "/rest/*")
])
class ErrorMappingTest extends Specification {

    def "Check error mapping"() {

        when: "accessing not existing asset"
        def res = new URL("http://localhost:8080/notexisting.html").text
        then: "error page"
        res.contains("custom error page")

        when: "accessing not existing template"
        res = new URL("http://localhost:8080/notexisting.ftl").text
        then: "error page"
        res.contains("custom error page")

        when: "accessing not existing path"
        res = new URL("http://localhost:8080/notexisting/").text
        then: "error page"
        res.contains("custom error page")

        when: "error processing template"
        res = new URL("http://localhost:8080/sample/error").text
        then: "error page"
        res.contains("custom error page")

        when: "error processing template"
        res = new URL("http://localhost:8080/sample/error2").text
        then: "error page"
        res.contains("custom error page")

        when: "direct 404 rest response"
        res = new URL("http://localhost:8080/sample/notfound").text
        then: "error page"
        res.contains("custom error page")
    }

    static class App extends Application<Configuration> {

        @Override
        void initialize(Bootstrap<Configuration> bootstrap) {
            // pure dropwizard bundle
            bootstrap.addBundle(ServerPagesBundle.app("app", "/app", "/")
                    .indexPage("index.html")
                    .errorPage("error.html")
                    .build())
        }

        @Override
        void run(Configuration configuration, Environment environment) throws Exception {
            environment.jersey().register(SampleTemplateResource)
        }
    }
}
