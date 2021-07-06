package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

class KafkaService {
    private final KafkaConsumer<String, String> consumer;
    private final ConsumerFunction parse;
    private final String groupIdName;

    KafkaService(String groupIdName, String topic, ConsumerFunction parse) {
        this.groupIdName = groupIdName;
        this.parse = parse;
        this.consumer = new KafkaConsumer<String, String>(properties(groupIdName));
        consumer.subscribe(Collections.singletonList(topic));
    }

    void run() {
        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                System.out.println("Encontrei " + records.count() + " registros.");
                for (var recordMessage : records) {
                    parse.consume(recordMessage);
                }
            }
        }
    }

    private static Properties properties(String groupIdName) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupIdName);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());

        return properties;
    }
}
