# Question 2 — Student Registration API

Endpoints:
- GET /api/students — list all students
- GET /api/students/{studentId} — get student by ID
- GET /api/students/major/{major} — get students by major
- GET /api/students/filter?minGpa={minGpa} — filter by minimum GPA
- POST /api/students — register new student (JSON body)
- PUT /api/students/{studentId} — update student

Run:
```powershell
mvn -f question2-student-api spring-boot:run
```

Notes:
- POST now auto-assigns `studentId` and returns 201 Created.

Sample request:
```http
GET /api/students? HTTP/1.1
Host: localhost:8080
```
