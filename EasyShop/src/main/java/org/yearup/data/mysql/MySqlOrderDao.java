package org.yearup.data.mysql;

import org.yearup.data.OrderDao;
import org.yearup.models.Order;
import org.yearup.models.OrderLineItem;
import org.yearup.models.Profile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class MySqlOrderDao extends MySqlDaoBase implements OrderDao {

    public MySqlOrderDao(DataSource dataSource){
        super(dataSource);
    }

    @Override
    public Order create(Order order) {
        String insert = "INSERT INTO orders " +
                "(user_id, date, address, city, state, zip, shipping_amount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insert);){

            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setDate(2, Date.valueOf(order.getDate()));
            preparedStatement.setString(3, order.getAddress());
            preparedStatement.setString(4, order.getCity());
            preparedStatement.setString(5, order.getState());
            preparedStatement.setString(6, order.getZip());
            preparedStatement.setDouble(7, order.getShippingAmount());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return order;
    }
}
