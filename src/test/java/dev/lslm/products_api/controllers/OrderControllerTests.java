package dev.lslm.products_api.controllers;

import dev.lslm.products_api.models.Product;
import dev.lslm.products_api.models.Stock;
import dev.lslm.products_api.repositories.OrderRepository;
import dev.lslm.products_api.repositories.ProductRepository;
import dev.lslm.products_api.repositories.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureMockMvc
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void setup() {
        Product product = new Product();
        product.setDescription("Product Description");
        product.setSupplier("supplier");
        product.setPrice(200.0);
        product.setMaxDiscount(0.1);
        product = productRepository.save(product);
        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setQuantity(10);
        stockRepository.save(stock);
    }

    @Test
    void shouldCreateOrder() throws Exception {
        String body = String.format("{\"productId\": 1, \"quantity\": 3, \"discount\": 0.10}");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        assertTrue(responseBody.contains("\"productId\""));
    }
}
