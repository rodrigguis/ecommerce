package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService {
    private static final String TOPIC;
    private static final String GROUP_ID_NAME;

    static {
        TOPIC = "ECOMMERCE_SEND_EMAIL";
        GROUP_ID_NAME = EmailService.class.getSimpleName();
    }

    public static void main(String[] args) {
        var emailService = new EmailService();
        try(var service = new KafkaService(GROUP_ID_NAME, TOPIC, emailService::parse, String.class)) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, String> recordMessage) {
        System.out.println("****************************************************");
        System.out.println("Send Email");
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
