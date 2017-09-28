package bootmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigServer
@ComponentScan({"bootmain"})
public class ConfigServerMain {
	
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerMain.class, args);
	}

}
