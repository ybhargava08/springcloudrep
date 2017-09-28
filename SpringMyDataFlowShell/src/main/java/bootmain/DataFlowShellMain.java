package bootmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.dataflow.shell.EnableDataFlowShell;

@SpringBootApplication
@EnableDataFlowShell
public class DataFlowShellMain {

	public static void main(String[] args) {
		SpringApplication.run(DataFlowShellMain.class, args);

	}

}
