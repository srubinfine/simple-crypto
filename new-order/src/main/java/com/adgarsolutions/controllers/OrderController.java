package com.adgarsolutions.controllers;

import com.adgarsolutions.services.orders.OrdersService;
import com.adgarsolutions.shared.exception.InvalidAccountIdException;
import com.adgarsolutions.shared.exception.InvalidNewOrderException;
import com.adgarsolutions.shared.exception.NewOrderAlreadyHasIdException;
import com.adgarsolutions.shared.health.HealthCheck;
import com.adgarsolutions.shared.model.Order;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("/orders")
@Secured(SecurityRule.IS_ANONYMOUS) // TODO: REMOVE WHEN JWT implemented
@Tag(name = "orders")
public class OrderController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    private final OrdersService ordersService;

    public OrderController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Get("/health")
    public HealthCheck serviceHealthCheck() {
        LOG.info("Order Controller Health Test");
        return new HealthCheck("Alive");
    }

    @Get("/{accountId}/all")
    @Operation(summary = "Fetch all orders for account id",description = "Fetch all orders for account id")
    @ApiResponse(responseCode = "400", description = "Invalid Account Id Supplied")
    @ApiResponse(responseCode = "500", description = "Internal error")    @Tag(name = "orders")
    public Flux<Order> getAllOrdersForAccount(@PathVariable("accountId") String accountId) throws InvalidAccountIdException {
        LOG.info("CTRL: Getting all orders for account ID {}", accountId);
        return ordersService.getAllOrdersForAccount(accountId);
    }

    @Post("/{accountId}/")
    @Operation(summary = "Creates a new bar object adding a decorated id and the current time",description = "Showcase of the creation of a dto")
    @ApiResponse(responseCode = "400", description = "Invalid Order Supplied")
    @ApiResponse(responseCode = "403", description = "Unauthorized")
    @ApiResponse(responseCode = "500", description = "Internal error") @Tag(name = "orders")
    public Mono<Order> createOrder(@PathVariable("accountId") String accountId, @Body Order order)
            throws InvalidAccountIdException, InvalidNewOrderException, NewOrderAlreadyHasIdException
    {
        LOG.info("CTRL: Create Order {} for account id {}", order, accountId);
        return ordersService.createOrder(accountId, order);
    }
}
