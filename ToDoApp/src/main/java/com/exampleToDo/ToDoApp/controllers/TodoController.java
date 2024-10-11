package com.exampleToDo.ToDoApp.controllers;

import com.exampleToDo.ToDoApp.entities.Todo;
import com.exampleToDo.ToDoApp.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {

    @Autowired
    private TodoService todoService;

    // Create a new to-do for a specific user
    @PostMapping("/{username}")
    public ResponseEntity<Todo> createTodoForUser(@PathVariable String username, @RequestBody Todo todo) {
        return new ResponseEntity<>(todoService.createTodoForUser(username, todo), HttpStatus.CREATED);
    }

    // Get all to-dos for a specific user
    @GetMapping("/{username}")
    public ResponseEntity<List<Todo>> getTodosForUser(@PathVariable String username) {
        return ResponseEntity.ok(todoService.getTodosForUser(username));
    }

    // Get a single to-do by ID for a specific user
    @GetMapping("/{username}/{id}")
    public ResponseEntity<Todo> getTodoByIdForUser(@PathVariable String username, @PathVariable String id) {
        return ResponseEntity.ok(todoService.getTodoByIdForUser(username, id));
    }

    // Update a to-do by ID for a specific user
    @PutMapping("/{username}/{id}")
    public ResponseEntity<Todo> updateTodoForUser(@PathVariable String username, @PathVariable String id, @RequestBody Todo todoDetails) {
        return ResponseEntity.ok(todoService.updateTodoForUser(username, id, todoDetails));
    }

    // Delete a to-do by ID for a specific user
    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<Void> deleteTodoByIdForUser(@PathVariable String username, @PathVariable String id) {
        todoService.deleteTodoByIdForUser(username, id);
        return ResponseEntity.noContent().build();
    }

    // Mark a to-do as completed for a specific user
    @PutMapping("/{username}/{id}/complete")
    public ResponseEntity<Todo> markTodoCompleteForUser(@PathVariable String username, @PathVariable String id) {
        return ResponseEntity.ok(todoService.markTodoCompleteForUser(username, id));
    }

    // Mark a to-do as incomplete for a specific user
    @PutMapping("/{username}/{id}/incomplete")
    public ResponseEntity<Todo> markTodoIncompleteForUser(@PathVariable String username, @PathVariable String id) {
        return ResponseEntity.ok(todoService.markTodoIncompleteForUser(username, id));
    }
}
