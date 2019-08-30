package com.manas.batch.load.ItemWriter;

import org.springframework.batch.item.ItemWriter;
import com.manas.batch.load.model.product;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MultiLineItemWriter implements ItemWriter<product> {


    public FlatFileItemWriter<String> writer;

    @Autowired
    public MultiLineItemWriter(FlatFileItemWriter<String> writer){
        this.writer=writer;
    }


    @Override
    public void write(List<? extends product> list)  throws Exception{
        List<String> lines = new ArrayList<>();

        for(product p :list){

            lines.add(String.valueOf(p.getId()));
            lines.add(p.getName());
            lines.add(p.getDesc());
            lines.add(String.valueOf(p.getPrice()));

        }

        this.writer.write(lines);


    }
}
