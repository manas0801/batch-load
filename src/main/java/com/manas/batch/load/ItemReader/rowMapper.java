package com.manas.batch.load.ItemReader;
import com.manas.batch.load.model.product;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class rowMapper implements RowMapper<product> {

    @Override
    public product mapRow(ResultSet resultSet, int i) throws SQLException{
        product pro = new product();
        pro.setId(resultSet.getInt("ID"));
        pro.setName(resultSet.getString("NAME"));
        pro.setDesc(resultSet.getString("DESC"));
        pro.setPrice(resultSet.getDouble("PRICE"));
        return pro;
    }
}
