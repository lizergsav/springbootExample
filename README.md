#Camunda springboot example

You can change the Tomcat variables in the application properties. The default port is 80.
Some services are developed. You can see under the http://localhost/swagger-ui.html

The Camunda built-in services are available on http://localhost/engine-rest
You can find details about the rest services here: https://docs.camunda.org/manual/latest/reference/rest/

check that the services are available (GET)
	- http://localhost/engine-rest/engine   


The example contain
	- Camunda embedded Springboot application
	- Spring MVC for some Camunda services
	- Cockpit és Rest services for Camunda
	- HTTP connector example