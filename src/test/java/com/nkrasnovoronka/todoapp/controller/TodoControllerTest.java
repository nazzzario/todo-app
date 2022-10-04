package com.nkrasnovoronka.todoapp.controller;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nkrasnovoronka.todoapp.security.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class TodoControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  JwtUtils jwtUtils;


  @Test
  void getAllBooksTest() throws Exception {
    given(jwtUtils.validateJwtToken(any())).willReturn(false);

    mockMvc.perform(get("/api/v1/todos/1/all")).andExpect(status().isOk());


  }

}
