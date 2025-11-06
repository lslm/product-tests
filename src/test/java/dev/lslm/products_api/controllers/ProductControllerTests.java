package dev.lslm.products_api.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureMockMvc
public class ProductControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateANewProduct() throws Exception {
        String requestBody = "{\n" +
                "    \"description\": \"Pilha\",\n" +
                "    \"supplier\": \"Duracell\",\n" +
                "    \"price\": 13.99,\n" +
                "    \"maxDiscount\": 0.10\n" +
                "}";

        // Testa apenas se o status HTTP é 200 (OK)
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/products")
                        .content(requestBody)
                        .contentType("application/json"))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldListAllProducts() throws Exception {
        String requestBody = "{\n" +
                "    \"description\": \"Pilha\",\n" +
                "    \"supplier\": \"Duracell\",\n" +
                "    \"price\": 13.99,\n" +
                "    \"maxDiscount\": 0.10\n" +
                "}";

        String expectedResponseBody =
                "[{\"id\":1,\"description\":\"Pilha\",\"supplier\":\"Duracell\",\"price\":13.99,\"maxDiscount\":0.1}]";

        // Requisição para criar um produto na base de dados
        mockMvc.perform(MockMvcRequestBuilders
                .post("/products")
                .content(requestBody)
                .contentType("application/json"));

        // Requisição para testar se o status HTTP é 200 (OK)
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/products"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        // Testa se o corpo da resposta é igual ao esperado
        assertEquals(expectedResponseBody, responseBody);
    }

    @Test
    public void shouldReturnAProductGivenItsId() throws Exception {
        String requestBody = "{\n" +
                "    \"description\": \"Pilha\",\n" +
                "    \"supplier\": \"Duracell\",\n" +
                "    \"price\": 13.99,\n" +
                "    \"maxDiscount\": 0.10\n" +
                "}";

        String expectedResponseBody =
                "{\"id\":1,\"description\":\"Pilha\",\"supplier\":\"Duracell\",\"price\":13.99,\"maxDiscount\":0.1}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/products")
                .content(requestBody)
                .contentType("application/json"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertEquals(expectedResponseBody, responseBody);
    }

    @Test
    public void shouldUpdateAProduct() throws Exception {
        String newProductRequestBody = "{\n" +
                "    \"description\": \"Pilha\",\n" +
                "    \"supplier\": \"Duracell\",\n" +
                "    \"price\": 13.99,\n" +
                "    \"maxDiscount\": 0.10\n" +
                "}";

        String updateProductRequestBody = "{\n" +
                "    \"description\": \"Pilha\",\n" +
                "    \"supplier\": \"Phillips\",\n" +
                "    \"price\": 20.99,\n" +
                "    \"maxDiscount\": 0.15\n" +
                "}";

        String expectedResponseBody =
                "{\"id\":1,\"description\":\"Pilha\",\"supplier\":\"Phillips\",\"price\":20.99,\"maxDiscount\":0.15}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/products")
                .content(newProductRequestBody)
                .contentType("application/json"));

        // Requisição para atualizar o produto criado anteriormente
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .patch("/products/1")
                        .content(updateProductRequestBody)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertEquals(expectedResponseBody, responseBody);
    }

    @Test
    public void shouldDeleteOneProduct() throws Exception {
        String requestBody = "{\n" +
                "    \"description\": \"Pilha\",\n" +
                "    \"supplier\": \"Duracell\",\n" +
                "    \"price\": 13.99,\n" +
                "    \"maxDiscount\": 0.10\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/products")
                .content(requestBody)
                .contentType("application/json"));

        // Testa se o status HTTP é 204 (No content)
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/products/1"))
                .andExpect(status().isNoContent());
    }
}

