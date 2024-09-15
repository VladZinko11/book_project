package com.zinko;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.zinko.model.Book;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;


@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class AppContext {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public CsvMapper csvMapper() {
        return new CsvMapper();
    }

    @Bean
    public CsvSchema csvSchema() {
        return csvMapper().schemaFor(Book.class)
                .withColumnSeparator('|')
                .withoutQuoteChar()
                .withHeader();
    }

    @Bean
    public ObjectWriter bookWriter() {
        return csvMapper().writer(csvSchema());
    }

    @Bean
    ObjectReader bookReader() {
        return csvMapper().readerFor(Book.class).with(csvSchema());
    }
}
