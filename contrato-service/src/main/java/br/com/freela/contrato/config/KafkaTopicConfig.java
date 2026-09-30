package br.com.freela.contrato.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic contratoCriadoTopic() {
        return TopicBuilder
                .name("contrato-criado")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic contratoEntregueTopic() {
        return TopicBuilder
                .name("contrato-entregue")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic contratoCanceladoTopic() {
        return TopicBuilder
                .name("contrato-cancelado")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic contratoConcluidoTopic() {
        return TopicBuilder
                .name("contrato-concluido")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
