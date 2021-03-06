package com.example.todo.application;

import nablarch.core.repository.di.config.externalize.annotation.SystemRepositoryComponent;

import java.util.List;

import com.example.todo.domain.Todo;
import com.example.todo.domain.TodoId;
import com.example.todo.domain.TodoStatus;
import com.example.todo.domain.TodoText;
import com.example.todo.domain.UserId;

@SystemRepositoryComponent
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> list(UserId userId) {
        List<Todo> todos = todoRepository.list(userId);
        return todos;
    }

    public Todo addTodo(UserId userId, TodoText text) {
        TodoId todoId = todoRepository.nextId();
        Todo newTodo = new Todo(todoId, text, TodoStatus.INCOMPLETE, userId);
        todoRepository.add(newTodo);
        return newTodo;
    }
    
    public Todo updateStatus(TodoId todoId, TodoStatus status, UserId userId) {
        Todo todo = todoRepository.get(todoId, userId);
        Todo changedTodo = todo.changeStatus(status);
        todoRepository.update(changedTodo);
        return changedTodo;
    }

    public void deleteTodo(TodoId todoId, UserId userId) {
        todoRepository.delete(todoId, userId);
    }

}
