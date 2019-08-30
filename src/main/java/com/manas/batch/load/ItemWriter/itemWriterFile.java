package com.manas.batch.load.ItemWriter;

import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import com.manas.batch.load.model.product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;

@Component
public class itemWriterFile extends FlatFileItemWriter<product> {

    public itemWriterFile(@Value("${output}")Resource resource){

        this.setResource(resource);
        this.setLineAggregator(
                product -> { return String.valueOf(product.getId())+"," + product.getName()+","+ product.getDesc() +
                        "," + String.valueOf(product.getPrice());
                });
        this.setHeaderCallback(new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.write("Id,Name,Desc,Price");
            }
        });
    }

}
