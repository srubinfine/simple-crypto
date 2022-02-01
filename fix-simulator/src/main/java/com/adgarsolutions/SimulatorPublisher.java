package com.adgarsolutions;

import com.adgarsolutions.shared.model.Order;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface SimulatorPublisher {
    @Topic("order")
    void sendOrder(@KafkaKey String orderId, Order order);
}
