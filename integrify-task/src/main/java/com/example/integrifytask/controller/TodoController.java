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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class TodoController {

    @Autowired
    private TodoImpl todoService;

    @RequestMapping(value = {"/auth/signin"}, method = RequestMethod.GET)
    public String login(){
        return "/auth/signin";
    }

    @RequestMapping(value = {"/auth/signup"}, method = RequestMethod.GET)
    public String createUser(Model model){
        model.addAttribute("user", new User());
        return "/auth/signup";
    }

    @RequestMapping(value = {"/auth/signup"}, method = RequestMethod.POST)
    public String registerNewUser(Model model, @Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute("successMessage", "User registered successfully!");
            model.addAttribute("bindingResult", bindingResult);
            return "/auth/signup";
        }
        List<Object> userPresentObj = todoService.isUserPresent(user);
        if((Boolean) userPresentObj.get(0)){
            model.addAttribute("successMessage", userPresentObj.get(1));
            return "/auth/signup";
        }

        todoService.signUp(user);
        model.addAttribute("successMessage", "User registered successfully!");

        return "/auth/signin";
    }


//
//    @PutMapping("/changePassword")
//    @Operation(summary = "change password from the system")
//    public ResponseEntity<User> changePassword(@PathVariable Integer id, @RequestBody TodoController newTodoController) {
//        return new ResponseEntity<>(todoService.modifyPassword(id, newTodoController), HttpStatus.CREATED);
//    }

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