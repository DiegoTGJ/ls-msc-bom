package pdtg.lsmsjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pdtg.lsmsjms.config.JmsConfig;
import pdtg.lsmsjms.model.HelloWorldMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by Diego T. 07-08-2022
 */
@Component
@RequiredArgsConstructor
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    @Scheduled(fixedRate = 5000)
    public void sendMessage(){
        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
    }

    @Scheduled(fixedRate = 5000)
    public void sendReceivedMessage() throws JMSException {
        System.out.println("I'm Sending a message");
        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        Message receivedMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, session -> {
            Message helloMessage = null;
            try {
                helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            helloMessage.setStringProperty("_type","pdtg.lsmsjms.model.HelloWorldMessage");
            System.out.println("Sending Hello");
            return helloMessage;
        });

        System.out.println(Objects.requireNonNull(receivedMsg).getBody(String.class));
    }
}
