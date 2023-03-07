package ru.novoselov.authservice.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import ru.novoselov.authservice.properties.KafkaTopics;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String kafkaUrl;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        return new KafkaAdmin(configs);
    }
    @Bean
    public NewTopic userSignupTopic() {
        return TopicBuilder
                .name(KafkaTopics.USER_SIGNUP.getName())
                .partitions(3)
                .compact()
                .build();
    }
}
