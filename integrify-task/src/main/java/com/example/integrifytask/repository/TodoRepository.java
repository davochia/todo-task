package com.example.integrifytask.repository;

import com.example.integrifytask.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    @Query(value = "SELECT * FROM Todo t WHERE t.status = ?1", nativeQuery = true)
    List<Todo> findByTodoByStatus(String status);
}
