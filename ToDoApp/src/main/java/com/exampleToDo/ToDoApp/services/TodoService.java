package com.exampleToDo.ToDoApp.services;

import com.exampleToDo.ToDoApp.entities.Todo;
import com.exampleToDo.ToDoApp.entities.User;
import com.exampleToDo.ToDoApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TodoService {

    @Autowired
    private UserRepository userRepository;

    // Create a new to-do for a specific user
//    import java.util.Optional;

    public Todo createTodoForUser(String username, Todo todo) {
        // Fetch the user by username
        Optional<User> optionalUser = userRepository.findByUsername(username);

        // If user not found, throw a clear exception
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));

        // Ensure that the todos list is initialized
        if (user.getTodos() == null) {
            user.setTodos(new ArrayList<>());
        }

        if (todo.getId() == null || todo.getId().isEmpty()) {
            todo.setId(UUID.randomUUID().toString());
        }
        // Add the new todo to the user's todo list
        user.getTodos().add(todo);

        // Save the updated user with the new todo in the list
        userRepository.save(user);

        return todo; // Return the newly added todo
    }



    // Get all to-dos for a specific user
    public List<Todo> getTodosForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getTodos();
    }

    // Get a specific to-do by ID for a specific user
    public Todo getTodoByIdForUser(String username, String todoId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getTodos().stream()
                .filter(todo -> todo.getId().equals(todoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    // Update a to-do for a specific user
    public Todo updateTodoForUser(String username, String todoId, Todo updatedTodo) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user has any todos
        if (user.getTodos() == null) {
            throw new RuntimeException("Todo list is empty");
        }

        // Find the todo by its ID and update its details
        Todo todoToUpdate = user.getTodos().stream()
                .filter(todo -> todo.getId() != null && todo.getId().equals(todoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        //update the details
        todoToUpdate.setTitle(updatedTodo.getTitle());
        todoToUpdate.setDescription(updatedTodo.getDescription());
        todoToUpdate.setCompleted(updatedTodo.isCompleted());
        todoToUpdate.setDate(updatedTodo.getDate());

        // Save the updated user with the modified todo
        userRepository.save(user);

        return todoToUpdate;
    }

    // Delete a to-do by ID for a specific user
    public void deleteTodoByIdForUser(String username, String todoId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getTodos() == null) {
            throw new RuntimeException("Todo list is empty");
        }

        // Remove the todo if the ID matches
        boolean removed = user.getTodos().removeIf(todo -> todo.getId() != null && todo.getId().equals(todoId));

        if (!removed) {
            throw new RuntimeException("Todo not found or unable to delete");
        }

        // Save the updated user without the deleted to-do
        userRepository.save(user);
    }

    // Mark a to-do as completed for a specific user
    public Todo markTodoCompleteForUser(String username, String todoId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getTodos().stream()
                .filter(todo -> todo.getId().equals(todoId))
                .forEach(todo -> todo.setCompleted(true));

        userRepository.save(user);
        return getTodoByIdForUser(username, todoId);
    }

    // Mark a to-do as incomplete for a specific user
    public Todo markTodoIncompleteForUser(String username, String todoId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getTodos().stream()
                .filter(todo -> todo.getId().equals(todoId))
                .forEach(todo -> todo.setCompleted(false));

        userRepository.save(user);
        return getTodoByIdForUser(username, todoId);
    }
}
