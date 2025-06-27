package org.example.projektuppgiftitsaker;

import org.example.projektuppgiftitsaker.dto.UserRegistrationDto;
import org.example.projektuppgiftitsaker.service.AppUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService userService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testShowRegisterForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testRegisterUserSuccess() throws Exception {
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "testuser")
                        .param("password", "Test12!!")
                        .param("role", "user")
                        .param("consentGiven", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(userService, times(1)).register(any(UserRegistrationDto.class));
    }

    @Test
    void testLoginFormAccessible() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUserByUsername("testuser");

        mockMvc.perform(delete("/user/testuser"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUserByUsername("testuser");
    }
}
