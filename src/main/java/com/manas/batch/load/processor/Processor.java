package com.manas.batch.load.processor;

import org.springframework.stereotype.Component;
import org.springframework.batch.item.ItemProcessor;
import com.manas.batch.load.model.product;

import java.util.HashMap;
import java.util.Map;

@Component
public class Processor implements ItemProcessor<product,product> {

    private static final Map<String, String> DescMap = new HashMap<>();

    public Processor()
    {
        DescMap.put("rice","lunch");
        DescMap.put("Bread","Brkfast");
        DescMap.put("Curd","General");

    }

    @Override
    public product process(product product) {
            product.setDesc(DescMap.get(product.getName()));

            return product;
    }
}
