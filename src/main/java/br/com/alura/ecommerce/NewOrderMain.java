package br.com.alura.ecommerce;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    private static final String TOPIC_ECOMMERCE_NEW_ORDER;
    private static final String TOPIC_ECOMMERCE_SEND_EMAIL;

    static {
        TOPIC_ECOMMERCE_NEW_ORDER = "ECOMMERCE_NEW_ORDER";
        TOPIC_ECOMMERCE_SEND_EMAIL = "ECOMMERCE_SEND_EMAIL";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var dispatcher = new KafkaDispatcher()) {
            for (var i = 0; i < 10; i++) {
                var key = UUID.randomUUID().toString();
                var value = key + ",67523,1234";
                dispatcher.send(TOPIC_ECOMMERCE_NEW_ORDER, key, value);

                var email = "Thank you for your order! We are processing your order!";
                dispatcher.send(TOPIC_ECOMMERCE_SEND_EMAIL, key, email);
            }
        }
    }
}
