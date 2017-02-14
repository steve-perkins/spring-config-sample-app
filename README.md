spring-config-sample-app
========================
This is one of three Git repositories, which work together to demonstrate using 
[Spring Cloud Config](https://cloud.spring.io/spring-cloud-config/) and [Vault](https://www.vaultproject.io) for 
configuration management:

* https://github.com/steve-perkins/spring-config-properties - Contains:
  * Properties files for global default properties that are available to all applications 
    (i.e. `application.propeties`), 
  * per-application files with properties that are common to that application in every environment 
    (e.g. `sampleapp.properties`), and 
  * environment-specific properties for each application (e.g. `sampleapp-staging.properties`).
* https://github.com/steve-perkins/spring-config-server - An instance of Spring Cloud Config server, configured 
  to serve up *non-secret* properties from the Git repo above, and *secret* properties from Vault.
* https://github.com/steve-perkins/spring-config-sample-app - A sample web application which uses the Spring Cloud 
  Config client to retrieve its config properties from the server above.
  
This demo shows the use case of having two types of config properties:

1. **non-secret** values, which can and should be maintainable by developer teams (e.g. JDBC URL's).
2. **secret** values, which should only be viewable or maintainable by people with specialized access (e.g. 
   usernames and passwords)
   
The non-secret values are stored as-is in the `spring-config-properties` repo.  The *secret* values are manually 
written to Vault.  At runtime, Spring Cloud Config retrieves properties from both sources, giving Vault the higher 
precedence whenever the same property is found in both.

Setup
=====
* Perform the steps described in the [spring-config-server](https://github.com/steve-perkins/spring-config-server) 
   project README.
* This repo is a Gradle-based project, containing a [Spring Boot](https://projects.spring.io/spring-boot/)-based 
   web app.  The Gradle script includes a `bootRun` task for conveniently launching the Spring Boot app.  When 
   calling this task, you'll need to pass JVM system properties needed by the client to reach Vault:
   
```
gradlew bootRun -DVAULT_URL=http://127.0.0.1:8200 -DVAULT_USERNAME=vault_user -DVAULT_PASSWORD=vault_pass
```

* You can access the web application in your browser at: [http://localhost:8080](http://localhost:8080).

* Make some changes to your properties, either in Git or in Vault.

* Force the sample app to retrieve the latest properties on-the-fly without a restart, by hitting the `/refresh` 
  endpoint with an empty POST request:
  
```
curl -X POST http://localhost:8080/refresh
```

* Refresh the root page in your browser to verify that your update was received.
