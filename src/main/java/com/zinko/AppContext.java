package com.zinko;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.zinko.model.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class AppContext {

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
