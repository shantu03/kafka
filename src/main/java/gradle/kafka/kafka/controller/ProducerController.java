package gradle.kafka.kafka.controller;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
@RequestMapping("/producer")
public class ProducerController {


    private static final String TOPIC_NAME="testy";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProducerController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @PostMapping("/event")
    public void sendEventToKafka(@RequestBody String eventData)
    {
        kafkaTemplate.send(TOPIC_NAME, "userEvent", eventData)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.err.println("Error sending message: " + ex.getMessage());
                    } else {
                        System.out.println("Message sent, offset: " +
                                result.getRecordMetadata().offset());
                    }
                });
    }


}
