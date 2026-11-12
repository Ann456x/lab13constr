package com.tecsup.minishop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.minishop.model.Product;
import com.tecsup.minishop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /api/products debe crear un producto y retornar 201")
    void shouldCreateProductAndReturn201() throws Exception {
        Product product = Product.builder().name("Webcam Logitech").price(250.00).stock(20).build();
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Webcam Logitech"));
    }

    @Test
    @DisplayName("GET /api/products/{id} debe retornar 500 cuando el ID no existe")
    void shouldReturn500WhenProductNotFound() throws Exception {
        mockMvc.perform(get("/api/products/9999"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("GET /api/products debe listar productos y retornar 200")
    void shouldGetAllProducts() throws Exception {
        productRepository.save(Product.builder().name("USB 64GB").price(35.00).stock(40).build());
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("USB 64GB"));
    }

    @Test
    @DisplayName("GET /api/products/{id} debe retornar 200 cuando el ID existe")
    void shouldGetProductById() throws Exception {
        Product saved = productRepository.save(Product.builder().name("SSD 1TB").price(280.00).stock(12).build());
        mockMvc.perform(get("/api/products/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("SSD 1TB"));
    }
}