ToDo application extension to include background image change & making the application persistant using SQLite instead of using a text file. Also tasks having priorities.

- Rishi Kanth Alapati.

Time spent for New Upload: 18 hours spent in total

Completed user stories:

Required: Creating the Todo App<br>
Required: Adding an "Edit Feature"<br>

Optional: Tweak the style of the app by adding a background, improve the UI.<br>
Optional: Persist the todo app in SQLite instead of using a text file<br>
Optional: Add support for priority for items<br>
Optional: Sort the TaskList according to priorities.(Displayed in the order of High, Med, Low)<br>
Optional: Empty tasks are not allowed to be added to the Task List.<br>

Notes:

-TASKS in ToDo App can have any of 3 priorities : High, Med , Low.<br>
                                   Displayed as : *** , **  ,   *<br>
                                   
---Each task when created is assigned a "Med" priority (**).<br>
---User has to click on the task and go to "edit activity" to edit/repriortize the task.<br>
---Radiobuttons have been established for the user to set the task which is then reflected back in the ToDo List after the user hits the "Save".<br>
---The tasks in the Task List gets sorted according to the priority whenever a new Task is added/updated or deleted.<br>
---Implemented SQLite database to save the tasks & its priorities and made the Task list persistent.<br>

Issues:<br>
Started with Custom Array adapter as well, but facing some issues, will try to figure those out and upload the new code as soon as possible.<br>

Walkthrough of all the userstories:

https://github.com/alaprisk/ToDo_App/blob/bgimage_sqlite_database_branch/ToDoApp/todo_taskpriority.gif
