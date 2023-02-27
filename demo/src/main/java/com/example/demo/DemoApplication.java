package com.example.demo;

import java.util.Collections;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	private static String portNumber = null;
	private static String DEFAULT_PORTNUM = "3000";
	public static void main(String[] args) {

		// if application.properties has a server port number, it will override everything else
		
		// only for Spring Boot. Will not work for JAR
			// mvn clean spring-boot:run -Dspring-boot.run.arguments=--port=5050
		// ApplicationArguments appArgs = new DefaultApplicationArguments(args);
		// if (appArgs.containsOption("port")) {
		// 	System.out.println("contains");
		// 	portNumber = appArgs.getOptionValues("port").get(0);
		// }

		// using JAR from mvn package
		// when port number is in cmd line argument
			// java -jar target/demo-0.0.1-SNAPSHOT.jar --port=5051
			// java -jar target/demo-0.0.1-SNAPSHOT.jar --Dport=5051
		for (String argVal : args) {
			System.out.println("argVal > " + argVal);
			if (argVal.contains("--Dport=") || argVal.contains("--port=")) {
				portNumber = argVal.substring(argVal.length() - 4, argVal.length());
				System.out.println("port number > " + portNumber);
			}
		}

		// when port number is set in environment
			// export PORT=#### OR set PORT=####
			// mvn clean spring-boot:run
		if(portNumber == null) {
			portNumber = System.getenv("PORT");
			System.out.println("env portNumber > " + portNumber);
		}

		// environment port is empty
			// unset PORT
			// export PORT= OR set PORT=
			// mvn spring-boot:run
		if (portNumber == null || portNumber.isBlank()) {
			portNumber = DEFAULT_PORTNUM;
		}

		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port",portNumber));
		app.run(args);
	}

}
