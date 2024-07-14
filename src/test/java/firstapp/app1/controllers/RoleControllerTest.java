package firstapp.app1.controllers;
import firstapp.app1.TestSecurityConfig;
import firstapp.app1.models.Role;
import firstapp.app1.services.RoleService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(RoleController.class)
@Import(TestSecurityConfig.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllRoles() throws Exception {
        Role role = new Role();
        role.setName("ROLE_USER");
        Mockito.when(roleService.getAllRoles()).thenReturn(Collections.singletonList(role));

        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ROLE_USER"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetRoleByName() throws Exception {
        Role role = new Role();
        role.setName("ROLE_USER");
        Mockito.when(roleService.getRoleByName(anyString())).thenReturn(Optional.of(role));

        mockMvc.perform(get("/roles/ROLE_USER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ROLE_USER"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateRole() throws Exception {
        Role role = new Role();
        role.setName("ROLE_USER");
        Mockito.when(roleService.createRole(any(Role.class))).thenReturn(role);

        mockMvc.perform(post("/roles")
                        .contentType("application/json")
                        .content("{\"name\":\"ROLE_USER\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ROLE_USER"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateRole() throws Exception {
        Role role = new Role();
        role.setName("ROLE_USER");
        Mockito.when(roleService.updateRole(anyString(), any(Role.class))).thenReturn(role);

        mockMvc.perform(put("/roles/ROLE_USER")
                        .contentType("application/json")
                        .content("{\"name\":\"ROLE_USER\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ROLE_USER"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteRole() throws Exception {
        Mockito.doNothing().when(roleService).deleteRole(anyLong());

        mockMvc.perform(delete("/roles/1"))
                .andExpect(status().isOk());
    }
}