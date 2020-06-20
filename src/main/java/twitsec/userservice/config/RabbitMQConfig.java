package twitsec.userservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${twitsec.rabbitmq.exchange}")
    private String exchange;

    @Value("${twitsec.rabbitmq.authqueue}")
    private String authQueueName;

    @Value("${twitsec.rabbitmq.authroutingkey}")
    private String authRoutingkey;

    @Value("${twitsec.rabbitmq.tweetqueue}")
    private String tweetQueueName;

    @Value("${twitsec.rabbitmq.tweetroutingkey}")
    private String tweetRoutingkey;

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue authQueue() {
        return new Queue(authQueueName, true);
    }

    @Bean
    Queue tweetQueue() {
        return new Queue(tweetQueueName, true);
    }

    @Bean
    Binding authBinding(Queue authQueue, DirectExchange exchange) {
        return BindingBuilder.bind(authQueue).to(exchange).with(authRoutingkey);
    }

    @Bean
    Binding tweetBinding(Queue tweetQueue, DirectExchange exchange) {
        return BindingBuilder.bind(tweetQueue).to(exchange).with(tweetRoutingkey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);

        System.out.println("Creating connection factory with: " + username + "@" + host + ":" + port);

        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
}
