package com.manas.batch.load.ItemReader;

import org.springframework.batch.item.file.FlatFileItemReader;
import com.manas.batch.load.model.product;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class itemReader extends FlatFileItemReader<product> {

    @Autowired
    public itemReader(DefaultLineMapper<product> lineMapper, @Value("${input}")  Resource resource){

        this.setLineMapper(lineMapper);
        this.setLinesToSkip(1);
        this.setResource(resource);
        this.setStrict(false);
    }
}
