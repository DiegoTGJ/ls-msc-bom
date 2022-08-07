package pdtg.lsmsjms.listener;

import javax.jms.JMSException;
import javax.jms.Message;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import pdtg.lsmsjms.config.JmsConfig;
import pdtg.lsmsjms.model.HelloWorldMessage;

import java.util.UUID;

/**
 * Created by Diego T. 07-08-2022
 */
@Component
@RequiredArgsConstructor
public class Listener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload  HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message){

//        System.out.println("Message Received");
//
//        System.out.println(helloWorldMessage);
    }

    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
    public void listenForHello(@Payload  HelloWorldMessage helloWorldMessage, @Headers MessageHeaders headers, Message message) throws JMSException {

        HelloWorldMessage reply = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("World")
                .build();

         jmsTemplate.convertAndSend(message.getJMSReplyTo(),reply);
        System.out.println("Message Received");

        System.out.println(helloWorldMessage);
    }
}
