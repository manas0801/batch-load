package com.manas.batch.load.config;

import com.manas.batch.load.ItemWriter.MultiLineItemWriter;
import com.manas.batch.load.processor.Processor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.support.SimpleJobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.manas.batch.load.model.product;
import com.manas.batch.load.processor.Processor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.validation.BindException;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

@EnableAutoConfiguration
@EnableBatchProcessing
@Configuration
public class batchConfig {




   /*@Autowired
   public DataSource ds;*/

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job job(Step step){



        return  jobBuilderFactory.get("Load-Job")
                .incrementer( new RunIdIncrementer())
                .start(step).build();
    }

    @Bean
    public Job UnloadJob(Step UnloadStep){



        return  jobBuilderFactory.get("UnLoad-Job")
                .incrementer( new RunIdIncrementer())
                .start(UnloadStep).build();
    }

    @Bean
    public Step UnloadStep(ItemReader<product> itemReaderDb,
                     ItemProcessor<product,product> Processor,
                    MultiLineItemWriter
                             MultiLineItemWriter){



        return stepBuilderFactory.get("UnLoad-Step")
                .<product,product>chunk(2)
                .reader(itemReaderDb)
                .processor(Processor)
                .writer(MultiLineItemWriter)
                .build();
    }


    @Bean
    public Step step(ItemReader<product>itemReader,
                     ItemProcessor<product,product> Processor,
                     ItemWriter<product>
            itemWriter){



        return stepBuilderFactory.get("Load-Step")
                .<product,product>chunk(2)
                .reader(itemReader)
                .processor(Processor)
                .writer(itemWriter)
                .build();
    }

   /* @Bean
    public FlatFileItemReader<product> itemReader (@Value("${input}") Resource resource , LineMapper<product> lineMapper){

        FlatFileItemReader<product> ffi = new FlatFileItemReader<>();
        ffi.setLinesToSkip(1);
        ffi.setLineMapper(lineMapper);
        ffi.setResource(new FileSystemResource("src/main/resources/user.csv"));
        ffi.setStrict(false);
        return ffi;


    }*/

    @Bean
    public DefaultLineMapper<product> lineMapper(DelimitedLineTokenizer lineTokenizer, FieldSetMapper fieldsetMapper){

        DefaultLineMapper<product> lm= new DefaultLineMapper<>();
        lm.setLineTokenizer(lineTokenizer);
        lm.setFieldSetMapper(fieldsetMapper);
       return lm;
    }

    @Bean
    public DelimitedLineTokenizer lineTokenizer() {
        DelimitedLineTokenizer dlt = new DelimitedLineTokenizer();
        dlt.setDelimiter(",");
        dlt.setNames(new String[]{"id","name","desc","price"});
        return dlt;

    }

    @Bean
    public FieldSetMapper fieldSetMapper(){



        FieldSetMapper<product> fsm = (FieldSet fieldSet)  ->{

                product p = new product();
                p.setId(fieldSet.readInt("id"));
                p.setDesc(fieldSet.readString("desc"));
                p.setName(fieldSet.readString("name"));
                p.setPrice(fieldSet.readDouble("price"));
                return p;
            };
        return fsm;
        }


  /*  @Bean
    public JdbcBatchItemWriter itemWriter(NamedParameterJdbcTemplate jdbc) {

        JdbcBatchItemWriter<product> jbiw = new JdbcBatchItemWriter<>();
        jbiw.setSql("INSERT INTO PRODTAB (ID,NAME,DESC,PRICE) VALUES (?,?,?,?)");
        jbiw.setItemPreparedStatementSetter(new ItemPreparedStatementSetter<product>() {
            @Override
            public void setValues(product product, PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, product.getId());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setString(3, product.getDesc());
                preparedStatement.setDouble(4, product.getPrice());

            }
        });
        jbiw.setJdbcTemplate(jdbc);
        return jbiw;

    }*/

   @Bean
    public NamedParameterJdbcTemplate jdbc(DataSource ds) {
       return new NamedParameterJdbcTemplate(ds);
   }

    @Bean
    public CommandLineRunner run(ApplicationContext appContext) {
        return args -> {

            String[] beans = appContext.getBeanDefinitionNames();
            Arrays.stream(beans).sorted().forEach(System.out::println);

        };
    }
    @Bean
    public FlatFileItemWriter<String> writer(){
       FlatFileItemWriter<String> ffw= new FlatFileItemWriter<>();
       ffw.setLineAggregator(String::toString);
       return ffw;
    }

}
