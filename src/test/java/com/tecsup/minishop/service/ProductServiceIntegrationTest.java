package com.tecsup.minishop.service;

import com.tecsup.minishop.model.Product;
import com.tecsup.minishop.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @MockitoBean
    private ProductRepository productRepository;

    @Test
    @DisplayName("Debe guardar un producto válido correctamente")
    void shouldSaveValidProduct() {
        Product input = Product.builder().name("Auriculares Sony").price(320.00).stock(15).build();
        Product expected = Product.builder().id(1L).name("Auriculares Sony").price(320.00).stock(15).build();
        when(productRepository.save(any(Product.class))).thenReturn(expected);
        Product result = productService.save(input);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el precio es cero o negativo")
    void shouldThrowExceptionWhenPriceIsInvalid() {
        Product product = Product.builder().name("Producto inválido").price(0.0).stock(5).build();
        assertThatThrownBy(() -> productService.save(product))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El precio debe ser mayor a cero");
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el stock es negativo")
    void shouldThrowExceptionWhenStockIsNegative() {
        Product product = Product.builder().name("Producto sin stock").price(100.00).stock(-1).build();
        assertThatThrownBy(() -> productService.save(product))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Debe listar todos los productos")
    void shouldFindAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(
                Product.builder().id(1L).name("Monitor").price(600.0).stock(5).build()
        ));
        assertThat(productService.findAll()).hasSize(1);
    }
}