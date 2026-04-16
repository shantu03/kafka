package gradle.kafka.kafka.controller;

import io.prometheus.client.Counter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerController {
    private final Counter kafkaEventCounter;

    public ConsumerController() {
    kafkaEventCounter=Counter.
            build().
            name("kafka_events_received_total")
            .help("Total number of Kafka events received")
            .register();
    }

    @KafkaListener(topics = "testy",groupId = "metrics-consumer-group")
    public void listen(String eventData)
    {
        System.out.println("Received Event : "+ eventData);
        kafkaEventCounter.inc(); 
    }

}
