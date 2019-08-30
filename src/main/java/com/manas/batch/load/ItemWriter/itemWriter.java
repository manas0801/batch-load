package com.manas.batch.load.ItemWriter;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import com.manas.batch.load.model.product;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@Component
@EnableAutoConfiguration
public class itemWriter extends JdbcBatchItemWriter<product> {




    private static final String sql = "INSERT INTO PRODTAB (ID,NAME,DESC,PRICE) VALUES (?,?,?,?)";

    @Autowired
    public itemWriter(NamedParameterJdbcTemplate namedParameterJdbcTemplate,ItemPreparedStatementSetter<product> ipss){

        this.setSql(sql);
        //this.setDataSource(ds);
        this.setJdbcTemplate(namedParameterJdbcTemplate);
        this.setItemPreparedStatementSetter(ipss);


    }

}
