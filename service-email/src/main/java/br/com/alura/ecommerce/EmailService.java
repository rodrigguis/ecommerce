package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class EmailService {
    private static final String TOPIC;
    private static final String GROUP_ID_NAME;

    static {
        TOPIC = "ECOMMERCE_SEND_EMAIL";
        GROUP_ID_NAME = EmailService.class.getSimpleName();
    }

    public static void main(String[] args) {
        var emailService = new EmailService();
        try(var service = new KafkaService(GROUP_ID_NAME, TOPIC, emailService::parse, Email.class, Map.of())) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Email> recordMessage) {
        System.out.println("****************************************************");
        System.out.println("Processing Email");
        System.out.println(recordMessage.key());
        System.out.println(recordMessage.value());
        System.out.println(recordMessage.partition());
        System.out.println(recordMessage.offset());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        System.out.println("Email sent . . .");
    }
}
