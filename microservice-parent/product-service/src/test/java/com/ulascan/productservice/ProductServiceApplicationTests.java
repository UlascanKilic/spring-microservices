package com.ulascan.productservice;

import com.mongodb.assertions.Assertions;
import com.ulascan.productservice.dto.ProductRequestDTO;
import com.ulascan.productservice.dto.ProductResponseDTO;
import com.ulascan.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequestDTO productRequest = getProductRequest();
        String productRequestDTOString = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(productRequestDTOString))
                .andExpect(status().isCreated());

        Assertions.assertTrue(productRepository.findAll().size() == 1);
    }

    @Test
    void shouldGetProducts() throws Exception {

        shouldCreateProduct();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        List<ProductResponseDTO> productResponseDTOList = Arrays.asList(objectMapper.readValue(content, ProductResponseDTO[].class));

        Assertions.assertTrue(productResponseDTOList.size() > 0);
        Assertions.assertTrue(productResponseDTOList.get(0).getName().equals(getProductRequest().getName()));
    }

    private ProductRequestDTO getProductRequest() {
        return ProductRequestDTO.builder()
                .name("iPhone 13")
                .description("iPhone 13 description")
                .price(BigDecimal.valueOf(1200))
                .build();
    }

}
