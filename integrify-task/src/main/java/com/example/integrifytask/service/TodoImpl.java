package com.example.integrifytask.service;

import com.example.integrifytask.model.Todo;
import com.example.integrifytask.model.TodoStatus;
import com.example.integrifytask.model.User;
import com.example.integrifytask.repository.TodoRepository;
import com.example.integrifytask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TodoImpl implements TodoI, UserDetailsService {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;


    /**
     * @param user
     */
    @Override
    public User signUp(User user) {
        Optional<User> userExist = userRepository.findByEmail(user.getEmail());

        if (userExist.isPresent()){
            throw new RuntimeException("User already exit");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }


    /**
     * @param email
     * @param password
     */
    @Override
    public void signIn(String email, String password) {

    }


    @Override
    public User changePassword(User oldUser, String newPasswordd) {
        Optional<User> userExist = userRepository.findByEmail(oldUser.getEmail());
        if (!userExist.isPresent()){
            throw new RuntimeException("User does not exit");
        }
        User user = userExist.get();
        String encodedPassword = bCryptPasswordEncoder.encode(newPasswordd);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException(
                        String.format("USER_NOT_FOUND", email)
                ));
    }

    @Override
    public List<Object> isUserPresent(User user) {
        boolean userExists = false;
        Optional<User> existingUserEmail = userRepository.findByEmail(user.getEmail());
        if(existingUserEmail.isPresent()){
            userExists = true;
        }

        return Arrays.asList(userExists);
    }

//    Todos
    /**
     * @param newTodo
     * @return
     */
    @Override
    public Todo addTodo(Integer userId, Todo newTodo) {
        Optional<User> user = userRepository.findById(userId);

        if (newTodo == null)
            throw new RuntimeException("Null data inserted");
        if (user.isEmpty())
            throw new RuntimeException("User not found");

        LocalDateTime now = LocalDateTime.now();

        newTodo.setCreated(now);
        newTodo.setUserId(userId);

        return todoRepository.save(newTodo);
    }

    /**
     * @return
     */
    @Override
    public List<Todo> getTodos(TodoStatus status) {
        List<Todo> todos = new ArrayList<Todo>();

        if (status == null)
            todoRepository.findAll().forEach(todos::add);
        else
            todoRepository.findByTodoByStatus(String.valueOf(status)).forEach(todos::add);

        return todos;
    }

    /**
     * @param id
     * @param newTodo
     * @return
     */
    @Override
    public Todo editTodo(Integer id, Todo newTodo) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo == null)
            throw new RuntimeException("Todo with id: " + id + " is not found");
        if (newTodo == null)
            throw new RuntimeException("Null data inserted");
//        if (newTodo.getUser() == null)
//            throw new RuntimeException("User not Authenticated");

        Todo todo = optionalTodo.get();
        LocalDateTime now = LocalDateTime.now();

        todo.setName(newTodo.getName());
        todo.setStatus(newTodo.getStatus());
        todo.setUpdated(now);
        todo.setDescription(newTodo.getDescription());
        return todoRepository.save(todo);
    }

    /**
     * @param id
     */
    @Override
    public void deleteTodo(Integer id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo == null)
            throw new RuntimeException("Todo with id: " + id + " is not found");

        todoRepository.deleteById(id);
    }

}
