package com.adgarsolutions.services.orders;

import com.adgarsolutions.services.interfaces.OrdersServiceKafkaSender;
import com.adgarsolutions.shared.exception.InvalidAccountIdException;
import com.adgarsolutions.shared.exception.InvalidNewOrderException;
import com.adgarsolutions.shared.exception.NewOrderAlreadyHasIdException;
import com.adgarsolutions.shared.model.Order;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Flux<Order> getAllOrdersForAccount(@PathVariable("accountId") String accountId) throws InvalidAccountIdException {
        LOG.info("SERVICE: Getting all orders for account ID {}", accountId);
        if (StringUtils.isEmpty(accountId)) {
            throw new InvalidAccountIdException("NULL account id");
        }

        return Flux.fromIterable(Arrays.asList(new Order(UUID.randomUUID().toString(), "AAPL", BigDecimal.valueOf(125.4), BigDecimal.valueOf(25.3), "Buy")));
    }

    public Mono<Order> createOrder(String accountId, Order order) throws InvalidAccountIdException, InvalidNewOrderException, NewOrderAlreadyHasIdException {
        LOG.info("SERVICE: Creating Order {}", order);
        if (StringUtils.isEmpty(accountId)) {
            throw new InvalidAccountIdException("NULL account id");
        }

        if (order == null) {
            throw new InvalidNewOrderException("NULL order supplied");
        }

        if (StringUtils.isNotEmpty(order.getId())) {
            throw new NewOrderAlreadyHasIdException(String.format("Order ID %s", order.getId()));
        }

        order.setId(UUID.randomUUID().toString());

        return Mono.just(order);
    }
}
