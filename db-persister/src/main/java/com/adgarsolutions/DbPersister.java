package com.adgarsolutions;

import com.adgarsolutions.repository.AsyncOrderRepository;
import io.micronaut.context.event.BeanContextEvent;
import io.micronaut.context.event.ShutdownEvent;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class DbPersister {

    private final static Logger LOG = LoggerFactory.getLogger(DbPersister.class);
    private final AsyncOrderRepository asyncOrderRepository;

    public DbPersister(AsyncOrderRepository asyncOrderRepository) {
        this.asyncOrderRepository = asyncOrderRepository;
    }

    @EventListener
    public void onStartup(BeanContextEvent beanContextEvent) throws Exception {
        if (beanContextEvent instanceof StartupEvent) {
            // Nothing to do at the moment
        }

        if (beanContextEvent instanceof ShutdownEvent) {
            if (this.asyncOrderRepository != null) {
                this.asyncOrderRepository.close();
            }
        }
    }
}
