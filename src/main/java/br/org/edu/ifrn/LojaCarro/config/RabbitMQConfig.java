package br.org.edu.ifrn.LojaCarro.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue filaLogs() {
        // Cria a fila "logs.fila" automaticamente se ela não existir
        return new Queue("logs.fila", true);
    }
}
