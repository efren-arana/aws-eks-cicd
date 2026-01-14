package com.paymentchain.billing;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentchain.billing.AbstractIntegrationTest;
import com.paymentchain.billing.common.InvoiceRequestMapper;
import com.paymentchain.billing.common.InvoiceResponseMapper;
import com.paymentchain.billing.controller.InvoiceRestController;
import com.paymentchain.billing.dto.InvoiceResponse;
import com.paymentchain.billing.entities.Invoice;
import com.paymentchain.billing.respository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class BasicApplicationTests extends AbstractIntegrationTest {

    private MockMvc mockMvc;
    
    @Mock
    private InvoiceRepository invoiceRepository;
    
    @InjectMocks
    private InvoiceRestController invoiceController;
    
    @Spy
    InvoiceRequestMapper irm = Mappers.getMapper(InvoiceRequestMapper.class);
    
    @Spy
    InvoiceResponseMapper irsm = Mappers.getMapper(InvoiceResponseMapper.class);
    
    
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(invoiceController).build();
    }
    
    
    @Test
    void contextLoads() {
       String message = "Default Message Cambio 11";
        assertNotNull(message);
    }

    @Test
    public void getAllInvoicesTest() throws Exception {
        // Arrange
        List<Invoice> dtos = new ArrayList<>();
        Invoice dto = createEntitywithDefaults();
        dtos.add(dto);

        when(invoiceRepository.findAll()).thenReturn(dtos);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/billing/v1")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
    }

    @Test
    public void getInvoiceByIdTest() throws Exception {
        // Arrange
        long defaultId = 1;
        Invoice dto = createEntitywithDefaults();
        dto.setId(defaultId);

        when(invoiceRepository.findById(defaultId)).thenReturn(Optional.of(dto));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/billing/v1/{id}", defaultId)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceId", is(1)));
    }

    @Test
    public void createInvoiceTest() throws Exception {
        // Arrange
        Invoice dto = createEntitywithDefaults();

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/billing/v1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void updateInvoiceTest() throws Exception {
        // Arrange
        long defaultId = 1;
        Invoice dto = new Invoice();
        dto.setCustomerId(defaultId);
        dto.setNumber("0987654321");

        when(invoiceRepository.findById(defaultId)).thenReturn(Optional.of(dto));
        when(invoiceRepository.save(dto)).thenReturn(dto);

        // Act
        InvoiceResponse body = (InvoiceResponse) invoiceController.put(String.valueOf(defaultId), irm.InvoiceToInvoiceRequest(dto)).getBody();

        // Assert
        assertEquals(body.getNumber(), dto.getNumber());
    }

    @Test
    public void givenInvalidInvoiceId_UpdateInvoice_NotFound() throws Exception {
        // Arrange
        String url = "/billing/v1/100";
        Invoice dto = new Invoice();
        dto.setCustomerId(2);
        dto.setNumber("0987654321");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deleteInvoiceTest() throws Exception {
        // Arrange
        long defaultId = 1;
        Invoice dto = createEntitywithDefaults();
        dto.setId(defaultId);

        when(invoiceRepository.findById(defaultId)).thenReturn(Optional.of(dto));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/billing/v1/{id}", defaultId)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private Invoice createEntitywithDefaults() {
        return new Invoice(1L, 1, "Jhon", "Service Invoice", 3000);
    }
}
