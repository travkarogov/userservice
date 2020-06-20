package twitsec.userservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class QueueConfig {

    private final AmqpAdmin amqpAdmin;

    @Value("${twitsec.rabbitmq.authqueue}")
    private String authQueueName;

    @Value("${twitsec.rabbitmq.tweetqueue}")
    private String tweetQueueName;

    @PostConstruct
    public void createQueues() {
        amqpAdmin.declareQueue(new Queue(authQueueName, true));
        amqpAdmin.declareQueue(new Queue(tweetQueueName, true));
    }
}
