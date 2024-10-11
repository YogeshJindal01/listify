package com.exampleToDo.ToDoApp.repositories;

import com.exampleToDo.ToDoApp.entities.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {
}

