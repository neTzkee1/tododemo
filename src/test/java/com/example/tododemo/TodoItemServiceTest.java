package com.example.tododemo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TodoItemServiceTest {

    @InjectMocks
    private TodoItemService todoItemService;

    @Mock
    private TodoItemRepository todoItemRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTodoItem() {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Test Todo");
        todoItem.setDescription("This is a test todo item.");

        when(todoItemRepository.save(any(TodoItem.class))).thenReturn(todoItem);

        TodoItem createdTodoItem = todoItemService.createTodoItem(todoItem);

        assertNotNull(createdTodoItem);
        assertEquals("Test Todo", createdTodoItem.getTitle());
        assertEquals("This is a test todo item.", createdTodoItem.getDescription());
    }

    @Test
    public void testUpdateTodoItem() {
        Long itemId = 1L;
        TodoItem existingTodoItem = new TodoItem();
        existingTodoItem.setId(itemId);
        existingTodoItem.setTitle("Existing Todo");
        existingTodoItem.setDescription("This is an existing todo item.");

        when(todoItemRepository.findById(itemId)).thenReturn(java.util.Optional.of(existingTodoItem));
        when(todoItemRepository.save(any(TodoItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TodoItem updatedTodoItem = new TodoItem();
        updatedTodoItem.setTitle("Updated Todo");
        updatedTodoItem.setDescription("This is an updated todo item.");

        TodoItem result = todoItemService.updateTodoItem(itemId, updatedTodoItem);

        assertNotNull(result);
        assertEquals("Updated Todo", result.getTitle());
        assertEquals("This is an updated todo item.", result.getDescription());
    }
}
