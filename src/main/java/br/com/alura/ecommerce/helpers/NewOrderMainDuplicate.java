package br.com.alura.ecommerce.helpers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrderMainDuplicate {

    public static void main(String[] args) {
        try (var producer = new KafkaProducer<String, String> (properties())) {
            var value = "132123,67523,7894589745";
            var recordMessage = new ProducerRecord<String, String>("ECOMMERCE_NEW_ORDER", value, value);

            producer.send(recordMessage, NewOrderMainDuplicate::onCompletion).get();
        } catch (ExecutionException | InterruptedException ex) {
            Thread.currentThread().interrupt();
            ex.printStackTrace();
        } finally {
            System.out.println("Finalizado");
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }

    private static void onCompletion(RecordMetadata data, Exception ex) {
        if (ex != null) {
            ex.printStackTrace();
            return;
        }
        System.out.println("sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset " + data.offset() + "/" + data.timestamp());
    }
}
