# Question 1 — Library Book Management API

Endpoints:
- GET /api/books — list all books
- GET /api/books/{id} — get book by id
- GET /api/books/search?title={title} — search books by title
- POST /api/books — add a new book (JSON body)
- DELETE /api/books/{id} — delete a book

Run:
```powershell
mvn -f question1-library-api spring-boot:run
```

Sample request:
```http
GET /api/books HTTP/1.1
Host: localhost:8080
```

Sample response: 200 OK
```json
[
  {"id":1,"title":"Clean Code","author":"Robert Martin","isbn":"978-0132350884","publicationYear":2008}
]
```
