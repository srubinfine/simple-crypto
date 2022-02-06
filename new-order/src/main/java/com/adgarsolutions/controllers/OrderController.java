package com.adgarsolutions.controllers;

import com.adgarsolutions.services.orders.OrdersService;
import com.adgarsolutions.shared.health.HealthCheck;
import com.adgarsolutions.shared.model.Order;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

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
    @Operation(summary = "Creates a new bar object adding a decorated id and the current time",description = "Showcase of the creation of a dto")
    @ApiResponse(responseCode = "201", description = "Bar object correctly created",content = @Content(mediaType = "application/json",schema = @Schema(type="BarDto")))
    @ApiResponse(responseCode = "400", description = "Invalid id Supplied")
    @ApiResponse(responseCode = "500", description = "Remote error, server is going nuts")    @Tag(name = "orders")
    public Flux<Order> getAllOrdersForAccount(@PathVariable("accountId") String accountId) {
        LOG.info("CTRL: Getting all orders for account ID {}", accountId);
        return ordersService.getAllOrdersForAccount(accountId);
    }
}
