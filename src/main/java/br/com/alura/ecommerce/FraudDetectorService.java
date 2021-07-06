package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService {
    private static final String TOPIC;
    private static final String GROUP_ID_NAME;

    static {
        TOPIC = "ECOMMERCE_NEW_ORDER";
        GROUP_ID_NAME = FraudDetectorService.class.getSimpleName();
    }

    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();
        try (var service = new KafkaService<>(GROUP_ID_NAME, TOPIC, fraudService::parse, Order.class)){
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Order> recordMessage) {
        System.out.println("****************************************************");
        System.out.println("Processing new Order, checking for fraud");
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

        System.out.println("Order processed . . .");
    }

}
