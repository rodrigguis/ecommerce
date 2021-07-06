package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Map;
import java.util.regex.Pattern;

public class LogService {
    private static final String TOPIC_PATTERN;
    private static final String GROUP_ID_NAME;

    static {
        TOPIC_PATTERN = "ECOMMERCE.*";
        GROUP_ID_NAME = LogService.class.getSimpleName();
    }

    public static void main(String[] args) {
        var logService = new LogService();
        try (var service = new KafkaService(GROUP_ID_NAME, Pattern.compile(TOPIC_PATTERN), logService::parse, String.class,
                Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))){
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, String> recordMessage) {
        System.out.println("******************************************");
        System.out.println("LOG: " + recordMessage.topic());
        System.out.println(recordMessage.key());
        System.out.println(recordMessage.value());
        System.out.println(recordMessage.partition());
        System.out.println(recordMessage.offset());
    }
}
