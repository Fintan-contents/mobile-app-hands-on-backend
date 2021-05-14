package com.example.todo.api;

import com.example.todo.domain.*;

import com.example.todo.application.TodoService;
import nablarch.core.repository.di.config.externalize.annotation.SystemRepositoryComponent;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;
import nablarch.core.validation.ee.ValidatorUtil;
import javax.validation.constraints.NotNull;

import nablarch.fw.jaxrs.EntityResponse;
import nablarch.fw.web.HttpResponse;

@SystemRepositoryComponent
@Path("/todos")
public class TodosAction {

    private final TodoService todoService;

    public TodosAction(TodoService todoService) {
        this.todoService = todoService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TodoResponse> get() {
        UserId userId = new UserId("1001");
        List<Todo> todos = todoService.list(userId);
        return todos.stream()
                .map(todo -> new TodoResponse(todo.id(), todo.text(), todo.status()))
                .collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EntityResponse post(PostRequest requestBody) {
        ValidatorUtil.validate(requestBody);

        EntityResponse response = new EntityResponse();
        UserId userId = new UserId("1001");
        TodoText text = new TodoText(requestBody.text);

        Todo todo = todoService.addTodo(userId, text);
        response.setEntity(new TodoResponse(todo.id(), todo.text(), todo.status()));
        response.setStatusCode(HttpResponse.Status.CREATED.getStatusCode());

        return response;

    }

    public static class PostRequest {
        @NotNull
        public String text;
    }

}
