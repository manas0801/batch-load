package com.manas.batch.load.ItemWriter;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.manas.batch.load.model.product;

@Component
@EnableAutoConfiguration
public class itemPreparedStatementSetter  implements ItemPreparedStatementSetter<product> {

    @Override
    public void setValues(product product, PreparedStatement preparedStatement) throws SQLException {

        preparedStatement.setInt(1, product.getId());
        preparedStatement.setString(2, product.getName());
        preparedStatement.setString(3, product.getDesc());
        preparedStatement.setDouble(4, product.getPrice());

    }
}
