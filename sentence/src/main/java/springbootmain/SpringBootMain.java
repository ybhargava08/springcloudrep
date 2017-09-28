package springbootmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("feignclients")
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableTurbine
@ComponentScan({"springbootmain","controller","service"})
public class SpringBootMain {

	public static void main(String[] args) {
	
		SpringApplication.run(SpringBootMain.class, args);

	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(SpringBootMain.class);
	}
}
