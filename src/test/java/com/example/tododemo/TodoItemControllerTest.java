package com.example.tododemo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoItemControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoItemService todoItemService;

    @Test
    public void testCreateTodoItem() throws Exception {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Test Todo");
        todoItem.setDescription("This is a test todo item.");

        when(todoItemService.createTodoItem(any(TodoItem.class))).thenReturn(todoItem);

        mockMvc.perform(post("/api/todo-items")
                        .contentType("application/json")
                        .content("{\"title\":\"Test Todo\",\"description\":\"This is a test todo item.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Todo"))
                .andExpect(jsonPath("$.description").value("This is a test todo item."));
    }

    @Test
    public void testUpdateTodoItem() throws Exception {
        Long itemId = 1L;
        TodoItem updatedTodoItem = new TodoItem();
        updatedTodoItem.setTitle("Updated Todo");
        updatedTodoItem.setDescription("This is an updated todo item.");

        when(todoItemService.updateTodoItem(eq(itemId), any(TodoItem.class))).thenReturn(updatedTodoItem);

        mockMvc.perform(put("/api/todo-items/{id}", itemId)
                        .contentType("application/json")
                        .content("{\"title\":\"Updated Todo\",\"description\":\"This is an updated todo item.\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Todo"))
                .andExpect(jsonPath("$.description").value("This is an updated todo item."));
    }
}