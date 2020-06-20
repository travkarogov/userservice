package twitsec.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQSender {

    private final AmqpTemplate rabbitTemplate;

    @Value("${twitsec.rabbitmq.exchange}")
    String exchange;

    @Value("${twitsec.rabbitmq.authroutingkey}")
    private String authRoutingkey;

    @Value("${twitsec.rabbitmq.tweetroutingkey}")
    private String tweetRoutingkey;

    public void sendDeleteRequestAuthService(String token) {
        rabbitTemplate.convertAndSend(exchange, authRoutingkey, token);
    }

    public void sendDeleteRequestTweetService(String token) {
        rabbitTemplate.convertAndSend(exchange, tweetRoutingkey, token);
    }
}
