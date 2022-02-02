package com.adgarsolutions;

import io.micronaut.context.annotation.Value;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adgarsolutions.shared.model.Order;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.UUID;

@Singleton
public class SimulatorStarter {

    private static final Logger LOG = LoggerFactory.getLogger(SimulatorStarter.class);

    private final SimulatorPublisher simulatorPublisher;

    public SimulatorStarter(SimulatorPublisher simulatorPublisher) {
        this.simulatorPublisher = simulatorPublisher;
    }

    @Value("${order.post.simulator-mode}")
    private Boolean simulatorMode;

    @EventListener
    void onStartup(StartupEvent startupEvent) {
        LOG.info("Simulator mode {}", enabledSimulationMode() ? "ENABLED" : "DISABLED");
    }

    @Scheduled(fixedDelay = "${order.post.execute}")
    void postOrder() {

        if (enabledSimulationMode()) {
            var o =  new Order(UUID.randomUUID().toString(), "AAPL", BigDecimal.valueOf(125.4), BigDecimal.valueOf(25.3), "Buy");
            LOG.info("Submitting order to Kafka: {}", o.getId());
            // Uncomment when make sure Kafka cluster is running: simulatorPublisher.sendOrder(o.getId(), o);
            // TODO: Will need to add service to check if cluster is up on startup
        }
    }

    private boolean enabledSimulationMode() {
        return this.simulatorMode != null && this.simulatorMode.booleanValue() == true;
    }
}
