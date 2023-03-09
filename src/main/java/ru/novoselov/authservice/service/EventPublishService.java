package ru.novoselov.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.novoselov.authservice.domain.event.Event;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventPublishService {
    private final KafkaTemplate<String, Event> kafkaTemplate;

    public void publish(Event event) {
        kafkaTemplate.send(event.getTopic(), UUID.randomUUID().toString(), event);
    }
}
