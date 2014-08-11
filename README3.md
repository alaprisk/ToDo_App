ToDo application extension to include background image change & making the application persistant using SQLite instead of using a text file. Also tasks having priorities.

- Rishi Kanth Alapati.

Time spent for New Upload: 18 hours spent in total

Completed user stories:

Required: Creating the Todo App
Required: Adding an "Edit Feature"

Optional: Tweak the style of the app by adding a background, improve the UI.
Optional: Persist the todo app in SQLite instead of using a text file
Optional: Add support for priority for items
Optional: Sort the TaskList according to priorities.(Displayed in the order of High, Med, Low)
Optional: Empty tasks are not allowed to be added to the Task List.

Notes:

-TASKS in ToDo App can have any of 3 priorities : High, Med , Low.
                                   Displayed as : *** , **  ,   *
                                   
---Each task when created is assigned a "Med" priority (**).
---User has to click on the task and go to "edit activity" to edit/repriortize the task.
---Radiobuttons have been established for the user to set the task which is then reflected back in the ToDo List after the user hits the "Save"
---The tasks in the Task List gets sorted according to the priority whenever a new Task is added/updated or deleted.
---Implemented SQLite database to save the tasks & its priorities and made the Task list persistent.

Issues:
Started with Custom Array adapter as well, but facing some issues, will try to figure those out and upload the new code as soon as possible.

Walkthrough of all the userstories:

https://github.com/alaprisk/ToDo_App/blob/bgimage_sqlite_database_branch/ToDoApp/todo_taskpriority.gif
