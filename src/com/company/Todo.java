package com.company;

public class Todo {

    private int id;
    private String activity;
    private Boolean checked;

    public Todo() {
    }

    public Todo(String activity, Boolean checked) {
        this.activity = activity;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "todo{" +
                "id=" + id +
                ", activity='" + activity + '\'' +
                ", checked=" + checked +
                '}';
    }
}
