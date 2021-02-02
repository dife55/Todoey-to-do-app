package com.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import express.utils.Utils;

import java.sql.*;
import java.util.List;

public class Database {

    private Connection conn;

    // constructor for Database (creating connecting to db file) ----------

    public Database(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:todoey.db");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    // get all todos -----------------------------------------------------

    public List<Todo> getTodos(){
        List<Todo> todos = null;

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos");
            ResultSet rs = stmt.executeQuery();

            Todo[] todosFromRS = (Todo[]) Utils.readResultSetToObject(rs, Todo[].class);
            todos = List.of(todosFromRS);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return todos;
    }


    // creating todos -----------------------------------------------------

    public void createTodo(Todo todo){
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO todos (activity, checked) VALUES (?, ?)");
            stmt.setString(1, todo.getActivity());
            stmt.setBoolean(2, todo.isChecked());

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    // deleting todos -----------------------------------------------------

    public void deleteTodo(Todo todo){

        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM todos WHERE id = ?");
            stmt.setInt(1, todo.getId());
            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    // get todos by id -----------------------------------------------------

    public Todo getTodoById(int id){
        Todo todo = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            Todo[] todoFromRS = (Todo[]) Utils.readResultSetToObject(rs, Todo[].class);

            todo = todoFromRS[0];



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return todo;
    }

    // update todos -----------------------------------------------------

    public void updateTodo(Todo todo){
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE todos SET checked = ? WHERE id = ?");
            stmt.setBoolean(1, todo.isChecked());
            stmt.setInt(2, todo.getId());
            stmt.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
