package br.com.alura.ecommerce;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        try(var orderDispatcher = new KafkaDispatcher<Order>()) {
            try(var emailDispatcher = new KafkaDispatcher<Email>()) {
                for (var i = 0; i < 10; i++) {
                    final var userId = UUID.randomUUID().toString();
                    final var orderId = UUID.randomUUID().toString();
                    final var amount = new BigDecimal(String.valueOf(BigDecimal.valueOf(Math.random() * 5000 + 1))).setScale(2, RoundingMode.HALF_UP);

                    var order = new Order(userId, orderId, amount.setScale(2));
                    orderDispatcher.send(TOPIC_ECOMMERCE_NEW_ORDER, userId, order);

                    var email = new Email("Novo Pedido", "Thank you for your order! We are processing your order!");
                    emailDispatcher.send(TOPIC_ECOMMERCE_SEND_EMAIL, userId, email);
                }
            }
        }
    }
}
