package com.example.integrifytask.controller;

import com.example.integrifytask.model.Todo;
import com.example.integrifytask.model.TodoStatus;
import com.example.integrifytask.model.User;
import com.example.integrifytask.service.TodoImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class TodoController {

    @Autowired
    private TodoImpl todoService;

    @PostMapping("/signup")
    public ResponseEntity<User> registerNewUser(@RequestBody User user){
        return new ResponseEntity<>(todoService.signUp(user), HttpStatus.CREATED);

    }

    @PutMapping("/changePassword")
    @Operation(summary = "change password from the system")
    public ResponseEntity<User> changePassword(@RequestBody User user, @RequestParam String newPassword) {
        return new ResponseEntity<>(todoService.changePassword(user, newPassword), HttpStatus.CREATED);
    }

    // Todos ////
    @PostMapping("/todos")
    @Operation(summary = "Add a new todo to system")
    public ResponseEntity<Todo> createTodo(@RequestParam(required = true) Integer userId , @RequestBody Todo newTodo) {
        return new ResponseEntity<>(todoService.addTodo(userId, newTodo), HttpStatus.CREATED);
    }

    @GetMapping("/todos")
    @Operation(summary = "Get all todos from system")
    public ResponseEntity<List<Todo>> getAllTodos(@RequestParam(required = false) TodoStatus status) {

        return new ResponseEntity<>(todoService.getTodos(status), HttpStatus.OK);
    }


    @PutMapping("/todos/{id}")
    @Operation(summary = "Edit todo in the system")
    public ResponseEntity<Todo> editTodo(@PathVariable Integer id, @RequestBody Todo newTodo) {
        return new ResponseEntity<>(todoService.editTodo(id, newTodo), HttpStatus.CREATED);
    }

    @DeleteMapping("/todos/{id}")
    @Operation(summary = "Delete todo from system")
    public ResponseEntity<String> deleteTodo(@PathVariable Integer id) {
        todoService.deleteTodo(id);
        return new ResponseEntity<>("TodoI with ID: " + id + " deleted", HttpStatus.NO_CONTENT);
    }

}