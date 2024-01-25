package com.example.votingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import java.time.Duration;

@SpringBootApplication
public class VotingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotingSystemApplication.class, args);
	}
	@Bean
	WebClient webclient(){
		HttpClient client = HttpClient.create().responseTimeout(Duration.ofSeconds(60));

		return WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector(client))
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.codecs(codecs->codecs.defaultCodecs().maxInMemorySize(16*1024*1024))
				.build();
	}

}
