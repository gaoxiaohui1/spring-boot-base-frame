package com.base.frame.springbatch.csv2db;

import com.base.frame.model.entity.base.OrderInfo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class Csv2dbConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<OrderInfo> csv2dbReader() {
        return new FlatFileItemReaderBuilder<OrderInfo>()
                .name("orderReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names("subOrderNumber,orderNumber,historyNumber,startDate,endDate,orderMoney,productID,productName,orderRealMoney,orderSource".split(","))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<OrderInfo>() {
                    {
                        setTargetType(OrderInfo.class);
                    }
                })
                .build();
    }

    @Bean
    public Csv2dbProcessor csv2dbProcessor() {
        return new Csv2dbProcessor();
    }

    @Bean
    public Csv2dbWriter csv2dbWriter(){
        return new Csv2dbWriter();
    }


    @Bean
    public Job csv2dbJob( Step orderStep) {
        return jobBuilderFactory.get("csv2dbJob")
                .incrementer(new RunIdIncrementer())
                .flow(orderStep)
                .end()
                .build();
    }

    @Bean
    public Step csv2dbStep(Csv2dbWriter writer) {
        return stepBuilderFactory.get("csv2dbStep")
                .<OrderInfo, OrderInfo>chunk(10)
                .reader(csv2dbReader())
                .processor(csv2dbProcessor())
                .writer(writer)
                .faultTolerant().retry(Exception.class).retryLimit(3)
                .build();
    }
}
