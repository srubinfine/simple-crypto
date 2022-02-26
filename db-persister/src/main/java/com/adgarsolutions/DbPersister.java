package com.adgarsolutions;

import com.adgarsolutions.repository.AsyncOrderRepository;
import com.adgarsolutions.shared.model.Order;
import io.micronaut.context.event.BeanContextEvent;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

@Singleton
public class DbPersister {

    private final static Logger LOG = LoggerFactory.getLogger(DbPersister.class);
    private final AsyncOrderRepository asyncOrderRepository;

    public DbPersister(AsyncOrderRepository asyncOrderRepository) {
        this.asyncOrderRepository = asyncOrderRepository;
    }

    @EventListener
    public void onStartup(BeanContextEvent beanContextEvent) {
        if (beanContextEvent instanceof StartupEvent) {

           final var id1 = UUID.randomUUID().toString();
           final var id2 = UUID.randomUUID().toString();
           final var id3 = UUID.randomUUID().toString();

            this.asyncOrderRepository.save(new Order(id1, "AAPL", BigDecimal.valueOf(125.4), BigDecimal.valueOf(25.3), "Buy")).subscribe(
                    System.out::println,
                    err -> System.err.println(err.toString()),
                    () -> System.out.println("DONE SAVING ORDER"));

            this.asyncOrderRepository.save(new Order(id2, "abc", BigDecimal.valueOf(125.4), BigDecimal.valueOf(25.3), "Buy")).subscribe(
                    System.out::println,
                    err -> System.err.println(err.toString()),
                    () -> System.out.println("DONE SAVING ORDER"));

            this.asyncOrderRepository.save(new Order(id3, "rmd", BigDecimal.valueOf(125.4), BigDecimal.valueOf(25.3), "Buy")).subscribe(
                    System.out::println,
                    err -> System.err.println(err.toString()),
                    () -> System.out.println("DONE SAVING ORDER"));

//            this.asyncOrderRepository.findAll(Arrays.asList("abc", "rmd")).subscribe(
//                    ord -> System.out.println(ord.toString()),
//                    err -> System.err.println(err.toString()),
//                    () -> System.out.println("DONE RETRIEVING ORDERS BY IDS"));
//
//            this.asyncOrderRepository.findById("abc").subscribe(
//                    ord -> System.out.println(ord.toString()),
//                    err -> System.err.println(err.toString()),
//                    () -> System.out.println("DONE RETRIEVING ORDER BY ID"));

            this.asyncOrderRepository.deleteMany(Arrays.asList(id2, id3)).subscribe(
                    ord -> System.out.println(ord.toString()),
                    err -> System.err.println(err.toString()),
                    () -> System.out.println("Done deleting orders by ID"));
        }
    }
}
