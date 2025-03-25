package dev.runners.runners;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import dev.runners.runners.user.User;
import dev.runners.runners.user.UserHttpClient;

@SpringBootApplication
public class RunnersApplication {

	private static final Logger logger = LoggerFactory.getLogger(RunnersApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RunnersApplication.class, args);

		logger.info("Application started successfully!");
	}

	@Bean
	UserHttpClient userHttpClient() {
		RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com/");
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
		return factory.createClient(UserHttpClient.class);
	}

	@Bean
	CommandLineRunner runner(UserHttpClient userHttpClient) {
		return args -> {
			// Run run = new Run(1, "First Run", LocalDateTime.now(),
			// LocalDateTime.now().plus(1, ChronoUnit.HOURS), 5,
			// Location.OUTDOOR);
			// // logger.info("Run: " + run);
			// runRepository.create(run);

			// List<User> users = userRestClient.findAll();
			// logger.info("Users: " + users);

			// User user = userRestClient.findById(1);
			// logger.info("User: " + user);

			List<User> users = userHttpClient.findAll();
			logger.info("Users: " + users);

			User user = userHttpClient.findById(1);
			logger.info("User: " + user);
		};
	}

}
