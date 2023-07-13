package kr.co.connectedin.research;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		servers = @Server(url = "/")
)
@SpringBootApplication
public class MaistDefaultApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaistDefaultApplication.class, args);
	}

}
