package com.example.integrifytask.service;

import com.example.integrifytask.model.Todo;
import com.example.integrifytask.model.TodoStatus;
import com.example.integrifytask.model.User;

import java.util.List;

public interface TodoI {
    void signUp(User user);
    List<Object> isUserPresent(User user);
    void signIn(String email, String password);
    void changePassword(String oldPassword, String newPassword);

    Todo addTodo(Integer userId, Todo newTodo);
    List<Todo> getTodos(TodoStatus status);
    Todo editTodo(Integer id, Todo newTodo);
    void deleteTodo(Integer id) ;
}
