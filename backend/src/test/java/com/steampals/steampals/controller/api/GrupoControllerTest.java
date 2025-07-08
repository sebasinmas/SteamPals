package com.steampals.steampals.controller.api;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class GrupoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetGrupo() throws Exception {
        mockMvc.perform(get("/api/grupo/grupo"))
                .andExpect(status().isOk())
                .andExpect(content().string("Grupo endpoint reached!"));
    }

    @Test
    void testPostGrupo() throws Exception {
        mockMvc.perform(post("/api/grupo/grupo"))
                .andExpect(status().isOk())
                .andExpect(content().string("Grupo post endpoint reached!"));
    }
}
