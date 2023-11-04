package com.example.tododemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoItemService {

    private final TodoItemRepository todoItemRepository;

    @Autowired
    public TodoItemService(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    public List<TodoItem> getAllTodoItems() {
        return todoItemRepository.findAll();
    }

    public TodoItem createTodoItem(TodoItem todoItem) {
        return todoItemRepository.save(todoItem);
    }

    public TodoItem updateTodoItem(Long id, TodoItem updatedTodoItem) {
        // Check if the item with the given id exists in the database
        TodoItem existingTodoItem = todoItemRepository.findById(id).orElse(null);

        if (existingTodoItem != null) {
            existingTodoItem.setTitle(updatedTodoItem.getTitle());
            existingTodoItem.setDescription(updatedTodoItem.getDescription());
            return todoItemRepository.save(existingTodoItem);
        } else {
            return null; // Item not found
        }
    }

    public void deleteTodoItem(Long id) {
        todoItemRepository.deleteById(id);
    }
}

