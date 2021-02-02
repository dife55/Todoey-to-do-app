let todos = [];


// get all todos from database
async function getTodos() {
    let result = await fetch('/rest/todos');
    todos = await result.json();

    console.log(todos);

    renderTodos();
    addDelete();
    doneFunction();
    
}

// show all todos
function renderTodos(){
    let todoList = document.querySelector("#todo-list");

    todoList.innerHTML = "";

    for(let todo of todos){
        let todoLi = `
            <li class="isChecked${todo.checked}">
                ${todo.activity}
            </li>
        `;
        
        todoList.innerHTML += todoLi;
    }
}

// add delete button plus functionality
async function addDelete(){

    let todoList = document.getElementsByTagName("li");
    
    for (let i = 0; i < todoList.length; i++) {
        var span = document.createElement("span");
        var txt = document.createTextNode("\u00D7");

        span.className = "close";
        span.appendChild(txt);
        todoList[i].appendChild(span);
    }
    

    let close = document.getElementsByClassName("close");

    for (let i = 0; i < close.length; i++) {
        close[i].onclick = function() {
        let div = this.parentElement;
        div.style.display = "none";

        deleteTodo(todos[i]);
        todos.splice(i, 1);
        }
    }
}


// done functionality and update to database
async function doneFunction(){

    let allTodos = $("li");


    for(let i = 0; i < allTodos.length; i++) {
        $(allTodos[i]).click(function() {

        if(todos[i].checked == true) {
            todos[i].checked = false;
            $(allTodos[i]).removeClass("isCheckedtrue").addClass("isCheckedfalse");
            

        } else if (todos[i].checked == false){
            todos[i].checked = true;
            $(allTodos[i]).removeClass("isCheckedfalse").addClass("isCheckedtrue");

        }

        })
    }
}
    

// creating todo and sending to database
async function createTodo(){
    location.reload();
    let userInput = document.getElementById("todo-input").value;
    if (userInput.length > 0) {

        let todo =  {
            activity: userInput,
            checked: false
        }

        todos.push(todo);
        addTodoToDB(todo);

    } else {
        alert("Du måste fylla i något!");
    }

    getTodos();
}


// delete from database
async function deleteTodo(todo){

    let result = await fetch("/rest/todos/id", {
        method: "DELETE",
        body: JSON.stringify(todo)
    });
}


// update to database
async function updateTodo(todo){


    let result = await fetch("/rest/todos/id", {
        method: "PUT",
        body: JSON.stringify(todo)
    });
}

// add to database
async function addTodoToDB(todo){
    
    let result = await fetch ("/rest/todos", {
        method: "POST",
        body: JSON.stringify(todo)
    });
}

// get todo by id
async function getTodoById(todo) {
    let result = await fetch('/rest/todos/id');
    todos = await result.json(todo);

}