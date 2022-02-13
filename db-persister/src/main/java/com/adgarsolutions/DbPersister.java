package com.adgarsolutions;

import com.adgarsolutions.repository.AsyncOrderRepository;
import io.micronaut.context.event.BeanContextEvent;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Arrays;

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

            this.asyncOrderRepository.findAll(Arrays.asList("abc", "rmd")).subscribe(
                    ord -> System.out.println(ord.toString()),
                    err -> System.out.println(err.toString()),
                    () -> System.out.println("DONE RETRIEVING ORDERS BY IDS"));

            this.asyncOrderRepository.findById("abc").subscribe(
                    ord -> System.out.println(ord.toString()),
                    err -> System.out.println(err.toString()),
                    () -> System.out.println("DONE RETRIEVING ORDER BY ID"));
        }
    }
}
