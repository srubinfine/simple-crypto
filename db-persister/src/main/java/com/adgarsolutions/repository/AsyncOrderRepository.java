package com.adgarsolutions.repository;

import com.adgarsolutions.shared.repository.DbContext;
import com.adgarsolutions.shared.model.Order;
import com.adgarsolutions.shared.repository.AsyncCrudRepository;
import com.adgarsolutions.shared.repository.QueryBinder;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

@Singleton // NEVER PUT @Repository annotation here
@QueryBinder(file="order")
public class AsyncOrderRepository extends AsyncCrudRepository<Order, String> {

    private final static Logger LOG = LoggerFactory.getLogger(AsyncOrderRepository.class);

    public AsyncOrderRepository(DbContext dbContext) {
        super(dbContext);
    }

    @Override
    public Order getNextItemFromRecordSet(ResultSet rs) throws SQLException {
        var ord = new Order();
        ord.setId(rs.getString("id"));
        return ord;
    }
}
