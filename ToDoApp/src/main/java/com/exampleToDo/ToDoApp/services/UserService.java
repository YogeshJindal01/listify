package com.exampleToDo.ToDoApp.services;

import com.exampleToDo.ToDoApp.entities.Todo;
import com.exampleToDo.ToDoApp.entities.User;
import com.exampleToDo.ToDoApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User addTodoToUser(String username, Todo todo) {
        User user = getUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        user.getTodos().add(todo);
        return userRepository.save(user);
    }

    public User removeTodoFromUser(String username, String todoId) {
        User user = getUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        user.getTodos().removeIf(todo -> todo.getId().equals(todoId));
        return userRepository.save(user);
    }
}

