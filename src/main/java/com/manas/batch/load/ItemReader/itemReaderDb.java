package com.manas.batch.load.ItemReader;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import com.manas.batch.load.model.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class itemReaderDb extends JdbcCursorItemReader<product> {

    @Autowired
    public itemReaderDb (RowMapper<product> rowMapper, DataSource ds){

        this.setSql("SELECT ID,NAME,PRICE,DESC FROM PRODTAB");
        this.setRowMapper(rowMapper);
        this.setDataSource(ds);
    }


}
