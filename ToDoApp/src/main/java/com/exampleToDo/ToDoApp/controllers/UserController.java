package com.exampleToDo.ToDoApp.controllers;

import com.exampleToDo.ToDoApp.entities.Todo;
import com.exampleToDo.ToDoApp.entities.User;
import com.exampleToDo.ToDoApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        return userService.getUserByUsername(user.getUsername())
                .filter(u -> u.getPassword().equals(user.getPassword()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @PostMapping("/{username}/todos")
    public ResponseEntity<User> addTodoToUser(@PathVariable String username, @RequestBody Todo todo) {
        return ResponseEntity.ok(userService.addTodoToUser(username, todo));
    }

    @DeleteMapping("/{username}/todos/{todoId}")
    public ResponseEntity<User> removeTodoFromUser(@PathVariable String username, @PathVariable String todoId) {
        return ResponseEntity.ok(userService.removeTodoFromUser(username, todoId));
    }
}
