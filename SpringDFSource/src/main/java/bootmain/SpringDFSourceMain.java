package bootmain;

import java.util.Calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@SpringBootApplication
@EnableBinding(Source.class)
public class SpringDFSourceMain {

	public static void main(String[] args) {
      SpringApplication.run(SpringDFSourceMain.class, args);
	}

	@InboundChannelAdapter(value = Source.OUTPUT,poller=@Poller(fixedDelay = "5000" , maxMessagesPerPoll="1"))
	public Message<Long> messageSource(){
		Calendar cal = Calendar.getInstance();
		
		return MessageBuilder.withPayload(cal.getTimeInMillis()).build();
	}
	
}
