# Question 5 — Task Management API

Endpoints:
- GET /api/tasks — list all tasks
- GET /api/tasks/{taskId} — get task by ID
- GET /api/tasks/status?completed={true|false} — by completion status
- GET /api/tasks/priority/{priority} — by priority (LOW/MEDIUM/HIGH)
- POST /api/tasks — create new task (JSON body)
- PUT /api/tasks/{taskId} — update task
- PATCH /api/tasks/{taskId}/complete — mark as completed
- DELETE /api/tasks/{taskId} — delete task

Run:
```powershell
mvn -f question5-Task-Management-api spring-boot:run
```

Notes:
- POST returns 201 Created; DELETE returns 204 No Content when successful.
