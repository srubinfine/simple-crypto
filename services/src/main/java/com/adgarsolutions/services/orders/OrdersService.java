package com.adgarsolutions.services.orders;

import com.adgarsolutions.services.interfaces.OrdersServiceKafkaSender;
import com.adgarsolutions.shared.model.Order;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

@Singleton
public class OrdersService {

    private static final Logger LOG = LoggerFactory.getLogger(OrdersService.class);

    private final OrdersServiceKafkaSender ordersServiceKafkaSender;

    public OrdersService(OrdersServiceKafkaSender ordersServiceKafkaSender) {
        this.ordersServiceKafkaSender = ordersServiceKafkaSender;
    }

    public Flux<Order> getAllOrdersForAccount(@PathVariable("accountId") String accountId) {
        LOG.info("SERVICE: Getting all orders for account ID {}", accountId);
        return Flux.fromIterable(Arrays.asList(new Order(UUID.randomUUID().toString(), "AAPL", BigDecimal.valueOf(125.4), BigDecimal.valueOf(25.3), "Buy")));
    }
}
