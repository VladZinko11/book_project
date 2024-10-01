package com.zinko.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;


@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "book_project";
    }

    @Bean
    public GridFsTemplate gridFsTemplate(MappingMongoConverter mappingMongoConverter) {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter);
    }
}
