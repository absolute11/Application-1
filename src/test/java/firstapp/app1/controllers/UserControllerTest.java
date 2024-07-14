package firstapp.app1.controllers;
import firstapp.app1.TestSecurityConfig;
import firstapp.app1.dto.CustomerDTO;
import firstapp.app1.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllCustomers() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setEmail("test@example.com");
        Mockito.when(customerService.getAllCustomers()).thenReturn(Collections.singletonList(customer));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test@example.com"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetCustomerByEmail() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setEmail("test@example.com");
        Mockito.when(customerService.getCustomerByEmail(anyString())).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customers/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setEmail("test@example.com");
        Mockito.when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(customer);

        mockMvc.perform(post("/customers")
                        .contentType("application/json")
                        .content("{\"email\":\"test@example.com\", \"username\": \"testuser\", \"githubId\": \"testgithubid\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setEmail("test@example.com");
        Mockito.when(customerService.updateCustomer(anyString(), any(CustomerDTO.class))).thenReturn(customer);

        mockMvc.perform(put("/customers/test@example.com")
                        .contentType("application/json")
                        .content("{\"email\":\"test@example.com\", \"username\": \"updateduser\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

}