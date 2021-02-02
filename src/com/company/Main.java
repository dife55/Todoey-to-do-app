package com.company;

import express.Express;
import express.middleware.Middleware;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // strarting up Express and Database -------------------

        Express app = new Express();
        Database db = new Database();


        // all todos -------------------------------------------

        app.get("/rest/todos", (req, res) -> {
            List<Todo> todos = db.getTodos();

            res.json(todos);
        });

        // todos by id ----------------------------------------

        app.get("/rest/todos/:id", (req, res) -> {
            try {
                int id = Integer.parseInt(req.getParam("id"));
                Todo todo = db.getTodoById(id);

                res.json(todo);

            } catch (NumberFormatException exception) {
                exception.printStackTrace();
            }


        });

        // creating todos -------------------------------------

        app.post("/rest/todos", (req, res) -> {
            Todo todo = (Todo) req.getBody(Todo.class);

            System.out.println(todo.toString());

            db.createTodo(todo);

            res.send("post OK!");
        });

        // deleting todos --------------------------------------

        app.delete("/rest/todos/:id", (req, res) -> {
            Todo todo = (Todo) req.getBody(Todo.class);
            db.deleteTodo(todo);
        });


        // updating todos ---------------------------------------

        app.put("/rest/todos/:id", (req, res) -> {
            Todo todo = (Todo) req.getBody(Todo.class);
            db.updateTodo(todo);

            res.send("Todo Updated!");
        });

        // use webpage -------------------------------------------

        try {
            app.use(Middleware.statics(Paths.get("src/www").toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        app.listen(112);
        System.out.println("Running on port 112");

        // -----------------------------------------------------


    }
}
