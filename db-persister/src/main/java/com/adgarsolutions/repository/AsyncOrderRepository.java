package com.adgarsolutions.repository;

import com.adgarsolutions.shared.model.Order;
import com.adgarsolutions.shared.repository.AsyncRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.sql.Connection;

@Singleton
public class AsyncOrderRepository extends AsyncRepository<Order, String> {

    private final static Logger LOG = LoggerFactory.getLogger(AsyncOrderRepository.class);

    public AsyncOrderRepository(Connection connection) {
        super(connection);
    }
}
