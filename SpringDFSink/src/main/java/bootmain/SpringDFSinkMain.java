package bootmain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableBinding(Sink.class)
public class SpringDFSinkMain {

	static Logger logger = LoggerFactory.getLogger(SpringDFSinkMain.class);
	
	public static void main(String[] args) {
      SpringApplication.run(SpringDFSinkMain.class, args);
	}

	@StreamListener(Sink.INPUT)
	public void loggerSink(String date){
		logger.info("date received by sink is : "+date);
	}
}
