package com.adgarsolutions.repository;

import com.adgarsolutions.shared.model.Order;
import com.adgarsolutions.shared.repository.AsyncRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Singleton
public class AsyncOrderRepository extends AsyncRepository<Order, String> {

    private final static Logger LOG = LoggerFactory.getLogger(AsyncOrderRepository.class);

    public AsyncOrderRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Order getNextItemFromRecordSet(ResultSet rs) throws SQLException {
        var ord = new Order();
        ord.setId(rs.getString("id"));
        return ord;
    }

    @Override
    protected PreparedStatement getPreparedStatement(String ctx, Connection connection, Object... args) throws SQLException {
        if (ctx.equalsIgnoreCase("findById")) { // todo: change to enum
            PreparedStatement ps = connection.prepareStatement("select * from smplc.order where id=?");
            ps.setString(1, args[0].toString());
            return ps;
        }

        return null;
    }
}
