package ru.novoselov.authservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import ru.novoselov.authservice.properties.KafkaTopics;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic userSignupTopic() {
        return TopicBuilder
                .name(KafkaTopics.USER_SIGNUP.getName())
                .partitions(3)
                .compact()
                .build();
    }
}
