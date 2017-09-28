package springbootmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringBootEureka {

	public static void main(String[] args) {
           SpringApplication.run(SpringBootEureka.class, args);
	}

	 protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		 return application.sources(SpringBootEureka.class);
	 }
}
