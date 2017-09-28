package bootmain;

import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;

@SpringBootApplication
@EnableBinding(Processor.class)
public class SpringDFProcessorMain {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
	
	public static void main(String[] args) {
		SpringApplication.run(SpringDFProcessorMain.class, args);
	}

	@Transformer(inputChannel = Processor.INPUT,outputChannel=Processor.OUTPUT)
	public Object transformLongToDate(Long milisecs){
		  String parsedDate = sdf.format(milisecs);
		  return parsedDate;
	}
}
