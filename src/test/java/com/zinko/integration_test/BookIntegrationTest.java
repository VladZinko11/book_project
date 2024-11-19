package com.zinko.integration_test;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest").withDatabaseName("book_project").withUsername("postgres").withPassword("root");

    static {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void testCreateBook() throws Exception {
        String jsonBook = "{\"id\":null,\"title\":\"Effective Java\",\"author\":null,\"series\":null,\"publication_date\":null, \"description\":\"\"}";

        mockMvc.perform(post("/api/v1/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBook))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void testGetByIdBook() throws Exception {
        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"));

        mockMvc.perform(get("/api/v1/books/2"))
                .andExpect(status().is(404));
    }

    @Test
    @Order(3)
    void testGetAllBook() throws Exception {
        mockMvc.perform(get("/api/v1/books/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @Order(4)
    void testUpdateBook() throws Exception {
        String jsonBook = "{\"id\":1,\"title\":\"Effective Java2\",\"author\":null,\"series\":null,\"publication_date\":null, \"description\":\"\"}";

        mockMvc.perform(put("/api/v1/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBook))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java2"));

        jsonBook = "{\"id\":2,\"title\":\"Effective Java2\",\"author\":null,\"series\":null,\"publication_date\":null, \"description\":\"\"}";

        mockMvc.perform(put("/api/v1/books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBook))
                .andExpect(status().is(404));
    }

    @Test
    @Order(5)
    void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/v1/books/1"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().is(404));
    }
}
